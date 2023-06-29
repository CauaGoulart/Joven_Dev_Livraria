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
import br.com.trier.Livraria.domain.Book;
import br.com.trier.Livraria.services.exceptions.IntegrityViolation;
import br.com.trier.Livraria.services.exceptions.ObjectNotFound;

public class BookServiceTest extends BaseTest{
	
	@Autowired
	private BookService bookService;
	
	@Test
	@DisplayName("Teste buscar book por ID")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	void findByIdTest() {
		Book book = bookService.findById(1);
		assertThat(book).isNotNull();
		assertEquals(1, book.getId());
		assertEquals("Book 1", book.getTitle());
	}
	
	@Test
	@DisplayName("Teste buscar book por ID inexistente")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	void findByIdNonExistentTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> bookService.findById(10));
		assertEquals("Livro id 10 não existe", exception.getMessage());
		
	}
	
	@Test
	@DisplayName("Teste listar todos")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	void listAllTest() {
		List<Book> list = bookService.listAll();
		assertEquals(2,list.size());
	}
	
	@Test
	@DisplayName("Teste incluir book")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	void insertBookTest() {
		Book book = new Book(null,"nome", 90);
		bookService.insert(book);
		assertThat(book).isNotNull();
		book = bookService.findById(1);
		assertEquals(1, book.getId());
		assertEquals("nome", book.getTitle());
	}
	
	@Test
	@DisplayName("Teste alterar book")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	void updateBookTest() {
		Book book = new Book(1,"altera", null);
		bookService.update(book);
		book = bookService.findById(1);
		assertThat(book).isNotNull();
		assertEquals(1, book.getId());
		assertEquals("altera", book.getTitle());
	}
	
	@Test
	@DisplayName("Teste deletar book")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	void deleteBookTest() {
		bookService.delete(1);
		List<Book> list = bookService.listAll();
		assertEquals(1,list.size());
		assertEquals(2,list.get(0).getId());
	}
	
	@Test
	@DisplayName("Teste procurar book que começa com")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	void findBookNameStatsWithTest() {
		Optional<Book> list = bookService.findByTitleIgnoreCase("Book 2");
		assertEquals("Book 2",list.get().getTitle());
	
		var exception = assertThrows(ObjectNotFound.class, () -> bookService.findByTitleIgnoreCase("x"));
		assertEquals("Nenhum livro com o titulo x.", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar book inexistente")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({ "classpath:/resources/sqls/book.sql" })
	void deleteNonExistentUserTest() {
		  var exception = assertThrows(ObjectNotFound.class, () -> bookService.delete(10));
		    assertEquals("Livro id 10 não existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste cadastrar livro com titulo vazio")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void findByNameInexistent() {
		var exception = assertThrows(IntegrityViolation.class, () -> bookService.insert(new Book(6, "", 40)));
	    assertEquals("Titulo não pode estar vazio.", exception.getMessage());	
	   	}
	
	@Test
	@DisplayName("Teste cadastrar livro com preço vazio")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void findByPriceInexistent() {
		var exception = assertThrows(IntegrityViolation.class, () -> bookService.insert(new Book(6, "nome", null)));
	    assertEquals("Preço não pode estar vazio.", exception.getMessage());	
	   	}
	
	@Test
	@DisplayName("Teste buscar livro por título")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	void findByTitleIgnoreCaseTest() {
	    Optional<Book> result = bookService.findByTitleIgnoreCase("Book 2");
	    assertEquals("Book 2", result.get().getTitle());
	}

	@Test
	@DisplayName("Teste buscar livro por preço")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	void findByPriceTest() {
	    Optional<Book> result = bookService.findByPrice(20);
	    assertEquals(20, result.get().getPrice());
	}

	@Test
	@DisplayName("Teste buscar livro por preço inexistente")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	void findByNonExistentPriceTest() {
	    var exception = assertThrows(ObjectNotFound.class, () -> bookService.findByPrice(100));
	    assertEquals("Nenhum livro com esse preço: 100.", exception.getMessage());
	}


}
