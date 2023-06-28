package br.com.trier.Livraria.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.Livraria.BaseTest;
import br.com.trier.Livraria.domain.BookAuthor;
import br.com.trier.Livraria.domain.Book;
import br.com.trier.Livraria.domain.Author;
import br.com.trier.Livraria.services.exceptions.ObjectNotFound;

public class BookAuthorServiceTest extends BaseTest{
	
	@Autowired
	BookAuthorService service;
	
	@Autowired
	BookService bookService;
	
	@Autowired
	AuthorService authorService;

	@Test
	@DisplayName("Teste buscar bookAuthor por id")
	@Sql({"classpath:/resources/sqls/clearTable_bookAuthor.sql"})
	
	@Sql({"classpath:/resources/sqls/bookAuthor.sql"})
	void findByIdTest() {
		var bookAuthor = service.findById(1);
		assertThat(bookAuthor).isNotNull();
		assertEquals(1, bookAuthor.getId());
		
	}
	
	@Test
	@DisplayName("Teste buscar bookAuthor por id inexistente")
	@Sql({"classpath:/resources/sqls/clearTable_bookAuthor.sql"})
	@Sql({"classpath:/resources/sqls/bookAuthor.sql"})
	void findByIdTestInexistente() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("BookAuthor com id 10 não existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste listar todos")
	@Sql({"classpath:/resources/sqls/clearTable_bookAuthor.sql"})
	@Sql({"classpath:/resources/sqls/bookAuthor.sql"})
	void listAll() {
		var lista = service.listAll();
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName("Teste cadastrar bookAuthor")
	void insert() {
		Author author = new Author(null,"nome", "Male");
		authorService.insert(author);
		Book book = new Book(null,"nome", 90);
		bookService.insert(book);
		BookAuthor bookAuthor = service.insert(new BookAuthor(null,book, author));
		assertThat(bookAuthor).isNotNull();
		assertEquals(1, bookAuthor.getId());

	}
	
	@Test
	@DisplayName("Teste update no bookAuthor")
	@Sql({"classpath:/resources/sqls/clearTable_bookAuthor.sql"})
	
	@Sql({"classpath:/resources/sqls/bookAuthor.sql"})
	void updateBookAuthor() {
		Author author = new Author(null,"nome", "Male");
		authorService.insert(author);
		Book book = new Book(null,"nome", 90);
		bookService.insert(book);
		BookAuthor bookAuthor = new BookAuthor(1,book,author);		
		assertThat(bookAuthor).isNotNull();
		var bookAuthorTest = service.findById(1);
		assertEquals(1, bookAuthorTest.getId());
		assertEquals(1, bookAuthor.getId());
	}
	
	@Test
	@DisplayName("Teste deletar bookAuthor")
	@Sql({"classpath:/resources/sqls/clearTable_bookAuthor.sql"})
	
	@Sql({"classpath:/resources/sqls/bookAuthor.sql"})
	void deleteBookAuthor() {
		service.delete(2);
		List<BookAuthor> lista = service.listAll();
		assertEquals(1, lista.size());
	}
	
	@Test
	@DisplayName("Teste deletar bookAuthor inexistente")
	@Sql({"classpath:/resources/sqls/clearTable_bookAuthor.sql"})
	
	@Sql({"classpath:/resources/sqls/bookAuthor.sql"})
	void deleteBookAuthorInexistente() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.delete(10));
		assertEquals("BookAuthor com id 10 não existe", exception.getMessage());
		List<BookAuthor> lista = service.listAll();
		assertEquals(2, lista.size());
	}
	

	@Test
	@DisplayName("Teste buscar bookAuthor por país")
	@Sql({"classpath:/resources/sqls/clearTable_bookAuthor.sql"})
	@Sql({"classpath:/resources/sqls/bookAuthor.sql"})
	void findByAuthorTest() {
	    Author author = authorService.findById(1);
	    List<BookAuthor> lista = service.findByAuthor(author);
	    assertEquals(1, lista.size());
	}

	@Test
	@DisplayName("Teste buscar bookAuthor por país inexistente")
	@Sql({"classpath:/resources/sqls/clearTable_bookAuthor.sql"})
	
	@Sql({"classpath:/resources/sqls/bookAuthor.sql"})
	void findByAuthorTestInexistente() {
	    Author author = new Author(10, "Author Inexistente", "Male");
	    var exception = assertThrows(ObjectNotFound.class, () -> service.findByAuthor(author));
	    assertEquals("Nenhum bookAuthor no país %s".formatted(author), exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar bookAuthor por book")
	@Sql({"classpath:/resources/sqls/clearTable_bookAuthor.sql"})
	
	@Sql({"classpath:/resources/sqls/bookAuthor.sql"})
	void findByBookTest() {
	    Book book = bookService.findById(1);
	    List<BookAuthor> lista = service.findByBook(book);
	    assertEquals(1, lista.size());
	}

	@Test
	@DisplayName("Teste buscar bookAuthor por book inexistente")
	@Sql({"classpath:/resources/sqls/clearTable_bookAuthor.sql"})
	
	@Sql({"classpath:/resources/sqls/bookAuthor.sql"})
	void findByBookTestInexistente() {
	    Book book = new Book(10, "Book Inexistente", 90);
	    var exception = assertThrows(ObjectNotFound.class, () -> service.findByBook(book));
	    assertEquals("Nenhum bookAuthor na book %s".formatted(book), exception.getMessage());
	}

}
