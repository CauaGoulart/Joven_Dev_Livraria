package br.com.trier.Livraria.resource;

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

import br.com.trier.Livraria.LivrariaApplication;
import br.com.trier.Livraria.config.jwt.LoginDTO;
import br.com.trier.Livraria.domain.Book;
import br.com.trier.Livraria.domain.dto.BookDTO;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@SpringBootTest(classes = LivrariaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class BookResourceTest {
	
	@Autowired
	protected TestRestTemplate rest;
	
	private static String authToken; 
	private HttpHeaders getHeaders(String email, String password) {
	    if (authToken == null) { 
	        LoginDTO loginDTO = new LoginDTO(email, password);
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
	        ResponseEntity<String> responseEntity = rest.exchange(
	                "/auth/token",
	                HttpMethod.POST,
	                requestEntity,
	                String.class
	        );
	        authToken = responseEntity.getBody(); 
	      
	    }

	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.setBearerAuth(authToken);
	    return headers;
	}


    
	private ResponseEntity<Book> getBook(String url) {
	    return rest.exchange(
	            url,
	            HttpMethod.GET,
	            new HttpEntity<>(getHeaders("test1@gmail.com", "123")),
	            Book.class
	    );
	}

	private ResponseEntity<List<Book>> getBooks(String url) {
	    return rest.exchange(
	            url,
	            HttpMethod.GET,
	            new HttpEntity<>(getHeaders("test1@gmail.com", "123")),
	            new ParameterizedTypeReference<List<Book>>() {}
	    );
	}


	@Test
	@DisplayName("Buscar por id")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	public void testGetOk() {
		ResponseEntity<Book> response = getBook("/book/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);

		Book book = response.getBody();
		assertEquals("Book 1", book.getTitle());
	}

	@Test
	@DisplayName("Buscar por id inexistente")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	public void testGetNotFound() {
		ResponseEntity<Book> response = getBook("/book/300");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	@DisplayName("Inserir novo livro")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	public void testInsert() {
	    BookDTO dto = new BookDTO(1,"New Book", 90);

	    HttpHeaders headers = getHeaders("test1@gmail.com", "123");
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<BookDTO> requestEntity = new HttpEntity<>(dto, headers);

	    ResponseEntity<Book> responseEntity = rest.exchange("/book", HttpMethod.POST, requestEntity, Book.class);

	    assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("New Book", responseEntity.getBody().getTitle());
	}


	@Test
	@DisplayName("Atualizar book")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	public void testUpdatebook() {
		ResponseEntity<Book> response = getBook("/book/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		Book book = new Book(1, "teste", null);
		HttpHeaders headers = getHeaders("test1@gmail.com", "123");
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Book> requestEntity = new HttpEntity<>(book, headers);
		ResponseEntity<Book> responseEntity = rest.exchange("/book/1", HttpMethod.PUT, requestEntity, Book.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("teste", responseEntity.getBody().getTitle());
	}

	@Test
	@DisplayName("Excluir book")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	public void testDeletebook() {
		HttpHeaders headers = getHeaders("test1@teste.com.br", "123");
		HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<Void> response = rest.exchange("/book/1", HttpMethod.DELETE, requestEntity, Void.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);	}

	@Test
	@DisplayName("Buscar book por titulo")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	public void testGetbookByName() {
		ResponseEntity<List<Book>> response = getBooks("/book/title/Book 1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	@DisplayName("Listar todos os book")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	public void testGetAllbook() {
		ResponseEntity<List<Book>> response = getBooks("/book");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	@DisplayName("Buscar book por preço")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/book.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	public void testGetBookByPrice() {
		ResponseEntity<List<Book>> response = getBooks("/book/price/20");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

}
