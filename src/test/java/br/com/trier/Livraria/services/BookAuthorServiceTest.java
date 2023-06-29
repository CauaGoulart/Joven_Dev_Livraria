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
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/bookAuthor.sql"})
	void findByIdTest() {
		var bookAuthor = service.findById(1);
		assertThat(bookAuthor).isNotNull();
		assertEquals(1, bookAuthor.getId());
		
	}
	
	@Test
	@DisplayName("Teste buscar bookAuthor por id inexistente")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/bookAuthor.sql"})
	void findByIdTestInexistente() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("Nenhum resultado com o id 10.", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste listar todos")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/bookAuthor.sql"})
	void listAll() {
		assertEquals(2, service.listAll().size());
	}
	
	@Test
	@DisplayName("Teste listar todos nulo")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	void listAllNull() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.listAll());
		assertEquals("Nenhum registro encontrado.", exception.getMessage());

	}
	
	
	@Test
	@DisplayName("Teste cadastrar bookAuthor")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	void insert() {
		Book book = new Book(null,"nome",34);
		bookService.insert(book);
		Author author = new Author(null,"nome","Male");
		authorService.insert(author);
		
		BookAuthor bookAuthor = new BookAuthor(null,book,author);
		service.insert(bookAuthor);
		assertThat(bookAuthor).isNotNull();
		assertEquals(1, bookAuthor.getId());
	    System.out.println("Lista de autores após o cadastro: " + authorService.listAll().size());


	}
	
	@Test
	@DisplayName("Teste update no bookAuthor")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/bookAuthor.sql"})
	void updateBookAuthor() {
		Book book = new Book(1,"nome2",34);
		bookService.update(book);
		Author author = new Author(1,"nome","Male");
		authorService.update(author);
		
		BookAuthor bookAuthor = service.update( new BookAuthor(1,book,author));
		assertThat(bookAuthor).isNotNull();	
		var bookAuthorTest = service.findById(1);
		assertEquals(1, bookAuthorTest.getId());
		assertEquals(1, bookAuthor.getId());
		assertEquals("nome2", bookAuthor.getBook().getTitle());
	    System.out.println("Lista de autores após o cadastro: " + authorService.listAll().size());

	}
	
	@Test
	@DisplayName("Teste deletar bookAuthor")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/bookAuthor.sql"})
	void deleteBookAuthor() {
		service.delete(2);
		List<BookAuthor> lista = service.listAll();
		assertEquals(1, lista.size());
	}
	
	@Test
	@DisplayName("Teste deletar bookAuthor inexistente")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/bookAuthor.sql"})
	void deleteBookAuthorInexistente() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.delete(10));
		assertEquals("Nenhum resultado com o id 10.", exception.getMessage());
		List<BookAuthor> lista = service.listAll();
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName("Teste buscar bookAuthor por livro")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/bookAuthor.sql"})
	void findByPaisTest() {
	    Book book = bookService.findById(1);
	    List<BookAuthor> lista = service.findByBook(book);
	    assertEquals(1, lista.size());
	}

	@Test
	@DisplayName("Teste buscar bookAuthor por livro inexistente")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/bookAuthor.sql"})
	void findByPaisTestInexistente() {
		Book book = new Book();
	    book.setId(100);
	    var exception = assertThrows(ObjectNotFound.class, () -> service.findByBook(book));
	    assertEquals("Livro não encontrado: %s.".formatted(book), exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar bookAuthor por autor")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/bookAuthor.sql"})
	void findByAuthorTest() {
	    Author author = authorService.findById(1);
	    List<BookAuthor> lista = service.findByAuthor(author);
	    assertEquals(1, lista.size());
	}

	@Test
	@DisplayName("Teste buscar bookAuthor por autor inexistente")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/bookAuthor.sql"})
	void findByAuthorInexistente() {
		Author author = new Author();
		author.setId(100);
	    var exception = assertThrows(ObjectNotFound.class, () -> service.findByAuthor(author));
	    assertEquals("Autor não encontrado: %s.".formatted(author), exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar bookAuthor por livro e autor")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/bookAuthor.sql"})
	void findByBookAndAuthor() {
		Book book = bookService.findById(1);
		Author author = authorService.findById(1);
	    List<BookAuthor> bookAuthor = service.findByBookAndAuthor(book,author);
	    assertEquals(1, bookAuthor.size());
	}
	
	@Test
	@DisplayName("Teste buscar phone por livro e autor inexistentes")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/bookAuthor.sql"})
	void findByClientAndNumberInexistant() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByBookAndAuthor(null,null));
		assertEquals("Nenhum resultado para esta pesquisa.", exception.getMessage());

	}

}
