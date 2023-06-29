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
import br.com.trier.Livraria.domain.Genres;
import br.com.trier.Livraria.domain.Book;
import br.com.trier.Livraria.domain.BookGenres;
import br.com.trier.Livraria.services.exceptions.ObjectNotFound;

public class BookGenresServiceTest extends BaseTest{
	
	@Autowired
	BookGenresService service;
	
	@Autowired
	BookService bookService;
	
	@Autowired
	GenresService genresService;

	@Test
	@DisplayName("Teste buscar bookGenres por id")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/bookGenres.sql"})
	void findByIdTest() {
		var bookGenres = service.findById(1);
		assertThat(bookGenres).isNotNull();
		assertEquals(1, bookGenres.getId());
		
	}
	
	@Test
	@DisplayName("Teste buscar bookGenres por id inexistente")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/bookGenres.sql"})
	void findByIdTestInexistente() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("Nenhum resultado com o id 10.", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste listar todos")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/bookGenres.sql"})
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
	@DisplayName("Teste cadastrar bookGenres")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	void insert() {
		Book book = new Book(null,"nome",34);
		bookService.insert(book);
		Genres genres = new Genres(null,"nome");
		genresService.insert(genres);
		BookGenres bookGenres = new BookGenres(null,book,genres);
		service.insert(bookGenres);
		assertThat(bookGenres).isNotNull();
		assertEquals(1, bookGenres.getId());
	    System.out.println("Lista de gêneros após o cadastro: " + genresService.listAll().size());

	}
	
	@Test
	@DisplayName("Teste update no bookGenres")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/bookGenres.sql"})
	void updateBookAuthor() {
		Book book = new Book(1,"nome2",34);
		bookService.update(book);
		Genres genres = new Genres(1,"nome");
		genresService.update(genres);
		
		BookGenres bookGenres = service.update( new BookGenres(1,book,genres));
		assertThat(bookGenres).isNotNull();	
		var bookAuthorTest = service.findById(1);
		assertEquals(1, bookAuthorTest.getId());
		assertEquals(1, bookGenres.getId());
		assertEquals("nome2", bookGenres.getBook().getTitle());assertEquals("nome2", bookGenres.getBook().getTitle());
	    System.out.println("Lista de gêneros após o cadastro: " + genresService.listAll().size());

	}
	
	@Test
	@DisplayName("Teste deletar bookGenres")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/bookGenres.sql"})
	void deleteBookAuthor() {
		service.delete(2);
		List<BookGenres> lista = service.listAll();
		assertEquals(1, lista.size());
	}
	
	@Test
	@DisplayName("Teste deletar bookGenres inexistente")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/bookGenres.sql"})
	void deleteBookAuthorInexistente() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.delete(10));
		assertEquals("Nenhum resultado com o id 10.", exception.getMessage());
		List<BookGenres> lista = service.listAll();
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName("Teste buscar bookGenres por livro")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/bookGenres.sql"})
	void findByPaisTest() {
	    Book book = bookService.findById(1);
	    List<BookGenres> lista = service.findByBook(book);
	    assertEquals(1, lista.size());
	}

	@Test
	@DisplayName("Teste buscar bookGenres por livro inexistente")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/bookGenres.sql"})
	void findByPaisTestInexistente() {
		Book book = new Book();
	    book.setId(100);
	    var exception = assertThrows(ObjectNotFound.class, () -> service.findByBook(book));
	    assertEquals("Livro não encontrado: %s.".formatted(book), exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar bookGenres por gênero")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/bookGenres.sql"})
	void findByGenresTest() {
	    Genres genres = genresService.findById(1);
	    List<BookGenres> lista = service.findByGenres(genres);
	    assertEquals(1, lista.size());
	}

	@Test
	@DisplayName("Teste buscar bookGenres por gênero inexistente")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/bookGenres.sql"})
	void findByGenresInexistente() {
		Genres genres = new Genres(1,"AAAAAAAAAA");
		genres.setId(100);
	    var exception = assertThrows(ObjectNotFound.class, () -> service.findByGenres(genres));
	    assertEquals("Gênero não encontrado: %s.".formatted(genres), exception.getMessage());
	    System.out.println("Lista de gêneros após o cadastro: " + genresService.listAll().size());

	}
	
	@Test
	@DisplayName("Teste buscar bookGenres por livro e gênero")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/bookGenres.sql"})
	void findByBookAndGenres() {
		Book book = bookService.findById(1);
		Genres genres = genresService.findById(1);
	    List<BookGenres> bookGenres = service.findByGenresAndBook(genres,book);
	    assertEquals(1, bookGenres.size());
	}
	
	@Test
	@DisplayName("Teste buscar phone por livro e gênero inexistentes")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/bookGenres.sql"})
	void findByClientAndNumberInexistant() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByGenresAndBook(null,null));
		assertEquals("Nenhum resultado para esta pesquisa.", exception.getMessage());

	}


}
