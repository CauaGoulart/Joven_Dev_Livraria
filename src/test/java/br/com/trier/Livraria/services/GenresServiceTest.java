package br.com.trier.Livraria.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.Livraria.BaseTest;
import br.com.trier.Livraria.domain.Genres;
import br.com.trier.Livraria.services.exceptions.IntegrityViolation;
import br.com.trier.Livraria.services.exceptions.ObjectNotFound;



public class GenresServiceTest extends BaseTest{
	
	@Autowired
	GenresService genresService;
	
	@Test
	@DisplayName("Teste buscar genres por ID")
	@Sql({"classpath:/resources/sqls/clearTable_genres.sql"})
	@Sql({"classpath:/resources/sqls/genres.sql"})
	void findByIdTest() {
		Genres genres = genresService.findById(1);
		assertThat(genres).isNotNull();
		assertEquals(1, genres.getId());
		assertEquals("Genres 1", genres.getGenres());
	}
	
	@Test
	@DisplayName("Teste buscar genres por ID inexistente")
	@Sql({"classpath:/resources/sqls/clearTable_genres.sql"})
	@Sql({"classpath:/resources/sqls/genres.sql"})
	void findByIdNonExistentTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> genresService.findById(10));
		assertEquals("Gênero com o id 10 não foi encontrado.", exception.getMessage());
		
	}
	
	@Test
	@DisplayName("Teste listar todos")
	@Sql({"classpath:/resources/sqls/clearTable_genres.sql"})
	@Sql({"classpath:/resources/sqls/genres.sql"})
	void listAllTest() {
		List<Genres> list = genresService.listAll();
		assertEquals(2,list.size());
	}
	
	@Test
	@DisplayName("Teste incluir genres")
	@Sql({"classpath:/resources/sqls/clearTable_genres.sql"})
	void insertGenresTest() {
		Genres genres = new Genres(null,"nome");
		genresService.insert(genres);
		assertThat(genres).isNotNull();
		genres = genresService.findById(1);
		assertEquals(1, genres.getId());
		assertEquals("nome", genres.getGenres());
	}
	
	@Test
	@DisplayName("Teste alterar genres")
	@Sql({"classpath:/resources/sqls/clearTable_genres.sql"})
	@Sql({"classpath:/resources/sqls/genres.sql"})
	void updateGenresTest() {
		Genres genres = new Genres(1,"altera");
		genresService.update(genres);
		genres = genresService.findById(1);
		assertThat(genres).isNotNull();
		assertEquals(1, genres.getId());
		assertEquals("altera", genres.getGenres());
	}
	
	@Test
	@DisplayName("Teste deletar genres")
	@Sql({"classpath:/resources/sqls/clearTable_genres.sql"})
	@Sql({"classpath:/resources/sqls/genres.sql"})
	void deleteGenresTest() {
		genresService.delete(1);
		List<Genres> list = genresService.listAll();
		assertEquals(1,list.size());
		assertEquals(2,list.get(0).getId());
	}
	
	@Test
	@DisplayName("Teste procurar genres que começa com")
	@Sql({"classpath:/resources/sqls/clearTable_genres.sql"})
	@Sql({"classpath:/resources/sqls/genres.sql"})
	void findGenresNameStatsWithTest() {
		Optional<Genres> list = genresService.findByGenresIgnoreCase("Genres 2");
		assertEquals("Genres 2",list.get().getGenres());
	
		var exception = assertThrows(ObjectNotFound.class, () -> genresService.findByGenresIgnoreCase("x"));
		assertEquals("Nenhum gênero encontrado: x.", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar genres inexistente")
	@Sql({"classpath:/resources/sqls/clearTable_genres.sql"})
	@Sql({ "classpath:/resources/sqls/genres.sql" })
	void deleteNonExistentUserTest() {
		  var exception = assertThrows(ObjectNotFound.class, () -> genresService.delete(10));
		    assertEquals("Gênero com o id 10 não foi encontrado.", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste cadastrar client por gênero inexistente")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void findByNameInexistente() {
		var exception = assertThrows(IntegrityViolation.class, () -> genresService.insert(new Genres(null, "")));
	    assertEquals("Gênero não pode estar vazio.", exception.getMessage());	
	   	}

}
