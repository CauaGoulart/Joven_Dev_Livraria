package br.com.trier.Livraria.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import br.com.trier.Livraria.LivrariaApplication;
import br.com.trier.Livraria.domain.Book;
import br.com.trier.Livraria.domain.dto.BookDTO;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/book.sql")
@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:/resources/sqls/clearTable.sql")
@SpringBootTest(classes = LivrariaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class BookResourceTest {
	
	@Autowired
	protected TestRestTemplate rest;
    
	private ResponseEntity<Book> getbook(String url) {
		return rest.getForEntity(url, Book.class);
	}

	private ResponseEntity<List<Book>> getbooks(String url) {
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Book>>() {
		});
	}

	@Test
	@DisplayName("Buscar por id")
	public void testGetOk() {
		ResponseEntity<Book> response = getbook("/book/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);

		Book book = response.getBody();
		assertEquals("Book 1", book.getTitle());
	}

	@Test
	@DisplayName("Buscar por id inexistente")
	@Sql(scripts = "classpath:/resources/sqls/clearTable.sql")
	public void testGetNotFound() {
		ResponseEntity<Book> response = getbook("/book/300");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	@DisplayName("Inserir novo país")
	@Sql(scripts = "classpath:/resources/sqls/clearTable.sql")
	public void testInsert() {
	    BookDTO dto = new BookDTO(1,"New Book", 90);

	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<BookDTO> requestEntity = new HttpEntity<>(dto, headers);

	    ResponseEntity<Book> responseEntity = rest.exchange("/book", HttpMethod.POST, requestEntity, Book.class);

	    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	    Book book = responseEntity.getBody();
	    assertEquals("New Book", book.getTitle());
	}


	@Test
	@DisplayName("Atualizar book")
	@Sql(scripts = "classpath:/resources/sqls/clearTable.sql")
	public void testUpdatebook() {
		Book book = new Book(1, "teste", null);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Book> requestEntity = new HttpEntity<>(book, headers);
		ResponseEntity<Book> responseEntity = rest.exchange("/book/1", HttpMethod.PUT, requestEntity, Book.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("teste", responseEntity.getBody().getTitle());
	}

	@Test
	@DisplayName("Excluir book")
	@Sql(scripts = "classpath:/resources/sqls/clearTable.sql")
	@Sql(scripts = "classpath:/resources/sqls/book.sql")
	public void testDeletebook() {
		ResponseEntity<Void> responseEntity = rest.exchange("/book/1", HttpMethod.DELETE, null, Void.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		ResponseEntity<Book> getbookResponse = getbook("/book/1");
		assertEquals(HttpStatus.NOT_FOUND, getbookResponse.getStatusCode());
	}

	@Test
	@DisplayName("Buscar book por titulo")
	@Sql(scripts = "classpath:/resources/sqls/clearTable.sql")
	@Sql(scripts = "classpath:/resources/sqls/book.sql")
	public void testGetbookByName() {
		ResponseEntity<List<Book>> response = getbooks("/book/title/Book 1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}


	@Test
	@DisplayName("Listar todos os book")
	@Sql(scripts = "classpath:/resources/sqls/clearTable.sql")
	public void testGetAllbook() {
		ResponseEntity<List<Book>> response = getbooks("/book");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	@DisplayName("Cadastrar book - BadRequest")
	@Sql(scripts = "classpath:/resources/sqls/clearTable.sql")
	public void testCreatebookBadRequest() {
		BookDTO dto = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<BookDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<BookDTO> responseEntity = rest.exchange("/book", HttpMethod.POST, requestEntity, BookDTO.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	@DisplayName("Atualizar book - BadRequest")
	@Sql(scripts = "classpath:/resources/sqls/clearTable.sql")
	public void testUpdatebookBadRequest() {
		BookDTO dto = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<BookDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<BookDTO> responseEntity = rest.exchange("/book/1", HttpMethod.PUT, requestEntity, BookDTO.class);
		assertThat(dto).isNull();
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	@Sql(scripts = "classpath:/resources/sqls/clearTable.sql")
	public void testUpdatebookNotFound() {
		BookDTO dto = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<BookDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<BookDTO> responseEntity = rest.exchange("/book/1", HttpMethod.PUT, requestEntity, BookDTO.class);
		assertThat(dto).isNull();
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

}
