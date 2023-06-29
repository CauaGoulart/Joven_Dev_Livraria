package br.com.trier.Livraria.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.Livraria.BaseTest;
import br.com.trier.Livraria.domain.Author;
import br.com.trier.Livraria.services.exceptions.ObjectNotFound;

public class AuthorServiceTest extends BaseTest {
	
	@Autowired
	AuthorService service;

	@Test
	@DisplayName("Teste buscar autor por ID")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/author.sql"})
	void findByIdTest() {
		Author author = service.findById(1);
		assertThat(author).isNotNull();
		assertEquals(1, author.getId());
		assertEquals("Author 1", author.getName());
		assertEquals("Male", author.getGender());
	}

	@Test
	@DisplayName("Teste buscar autor por ID inexistente")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/author.sql"})
	void findByIdNonExistentTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("Autor com id 10 não foi encontrado.", exception.getMessage());
	}

	@Test
	@DisplayName("Teste listar todos")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/author.sql"})
	void listAllTest() {
		List<Author> list = service.listAll();
		assertEquals(2, list.size());
	}

	@Test
	@DisplayName("Teste cadastrar autor")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	void insertTest() {
		Author author = service.insert(new Author(null, "John Doe", "Male"));
		assertThat(author).isNotNull();
		assertEquals(1, author.getId());
		assertEquals("John Doe", author.getName());
		assertEquals("Male", author.getGender());
	}

	@Test
	@DisplayName("Teste atualizar autor")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/author.sql"})
	void updateTest() {
		Author author = new Author(1, "Jane Smith", "Female");
		service.update(author);
		author = service.findById(1);
		assertThat(author).isNotNull();
		assertEquals(1, author.getId());
		assertEquals("Jane Smith", author.getName());
		assertEquals("Female", author.getGender());
	}

	@Test
	@DisplayName("Teste deletar autor")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/author.sql"})
	void deleteTest() {
		service.delete(1);
		List<Author> list = service.listAll();
		assertEquals(1, list.size());
		assertEquals(2, list.get(0).getId());
	}

	@Test
	@DisplayName("Teste buscar autor por nome")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/author.sql"})
	void findByNameTest() {
		Optional<Author> result = service.findByNameIgnoreCase("Author 2");
		assertEquals("Author 2", result.get().getName());
		
	}
	
	@Test
	@DisplayName("Teste buscar autor por genero")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/author.sql"})
	void findByGenderTest() {
		Optional<Author> lista = service.findByGenderIgnoreCase("Male");
		assertFalse(lista.isEmpty());
		assertEquals("Male", lista.get().getGender());
		
	}
	
	@Test
	@DisplayName("Teste buscar autor por nome inexistente")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/author.sql"})
	void findByNameNonExistantTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByNameIgnoreCase("Inexistant"));
		assertEquals("Nenhum autor com o nome Inexistant encontrado", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste buscar autor por genero inaxistente")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/author.sql"})
	void findByGenderNonExistantTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByGenderIgnoreCase("Inexistant"));
		assertEquals("Nenhum autor com o gênero Inexistant encontrado", exception.getMessage());
		
	}
	

}
