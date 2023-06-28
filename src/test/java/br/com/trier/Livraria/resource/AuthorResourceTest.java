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

import br.com.trier.Livraria.LivrariaApplication;
import br.com.trier.Livraria.domain.Author;
import br.com.trier.Livraria.domain.dto.AuthorDTO;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@SpringBootTest(classes = LivrariaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthorResourceTest {

	@Autowired
	protected TestRestTemplate rest;

	private ResponseEntity<Author> getauthor(String url) {
		return rest.getForEntity(url, Author.class);
	}

	private ResponseEntity<List<Author>> getauthors(String url) {
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Author>>() {
		});
	}

	@Test
	@DisplayName("Buscar por id")
	public void testGetOk() {
		ResponseEntity<Author> response = getauthor("/author/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);

		Author author = response.getBody();
		assertEquals("Author 1", author.getName());
	}

	@Test
	@DisplayName("Buscar por id inexistente")
	@Sql(scripts = "classpath:/resources/sqls/clearTable_author.sql")
	@Sql(scripts = "classpath:/resources/sqls/author.sql")
	public void testGetNotFound() {
		ResponseEntity<Author> response = getauthor("/author/300");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	@DisplayName("Cadastrar author")
	@Sql(scripts = "classpath:/resources/sqls/clearTable_author.sql")
	@Sql(scripts = "classpath:/resources/sqls/author.sql")
	public void testCreateauthor() {
		AuthorDTO dto = new AuthorDTO(null, "Cadastra", "Male");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<AuthorDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<AuthorDTO> responseEntity = rest.exchange("/author", HttpMethod.POST, requestEntity, AuthorDTO.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		AuthorDTO author = responseEntity.getBody();
		assertThat(author).isNotNull();
		assertEquals("Cadastra", author.getName());

	}

	@Test
	@DisplayName("Atualizar author")
	@Sql(scripts = "classpath:/resources/sqls/clearTable_author.sql")
	@Sql(scripts = "classpath:/resources/sqls/author.sql")
	public void testUpdateauthor() {
		AuthorDTO dto = new AuthorDTO(null, "teste", "Male");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<AuthorDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<AuthorDTO> responseEntity = rest.exchange("/author/1", HttpMethod.PUT, requestEntity, AuthorDTO.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		AuthorDTO updatedauthor = responseEntity.getBody();
		assertThat(updatedauthor).isNotNull();
		assertEquals("teste", updatedauthor.getName());
	}

	@Test
	@DisplayName("Excluir author")
	@Sql(scripts = "classpath:/resources/sqls/clearTable_author.sql")
	@Sql(scripts = "classpath:/resources/sqls/author.sql")
	public void testDeleteauthor() {
		ResponseEntity<Void> responseEntity = rest.exchange("/author/1", HttpMethod.DELETE, null, Void.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		ResponseEntity<Author> getauthorResponse = getauthor("/author/1");
		assertEquals(HttpStatus.NOT_FOUND, getauthorResponse.getStatusCode());
	}

	@Test
	@DisplayName("Buscar author por nome")
	@Sql(scripts = "classpath:/resources/sqls/clearTable_author.sql")
	@Sql(scripts = "classpath:/resources/sqls/author.sql")
	public void testGetauthorsByName() {
		ResponseEntity<List<Author>> response = getauthors("/author/like/cri");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	@DisplayName("Listar todos os author")
	@Sql(scripts = "classpath:/resources/sqls/clearTable_author.sql")
	@Sql(scripts = "classpath:/resources/sqls/author.sql")
	public void testGetAllauthor() {
		ResponseEntity<List<Author>> response = getauthors("/author");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	@DisplayName("Cadastrar author - BadRequest")
	@Sql(scripts = "classpath:/resources/sqls/clearTable_author.sql")
	@Sql(scripts = "classpath:/resources/sqls/author.sql")
	public void testCreateauthorBadRequest() {
		AuthorDTO dto = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<AuthorDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<AuthorDTO> responseEntity = rest.exchange("/author", HttpMethod.POST, requestEntity, AuthorDTO.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	@DisplayName("Atualizar author - BadRequest")
	@Sql(scripts = "classpath:/resources/sqls/clearTable_author.sql")
	@Sql(scripts = "classpath:/resources/sqls/author.sql")
	public void testUpdateauthorBadRequest() {
		AuthorDTO dto = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<AuthorDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<AuthorDTO> responseEntity = rest.exchange("/author/1", HttpMethod.PUT, requestEntity, AuthorDTO.class);
		assertThat(dto).isNull();
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	@Sql(scripts = "classpath:/resources/sqls/clearTable_author.sql")
	@Sql(scripts = "classpath:/resources/sqls/author.sql")
	public void testUpdateauthorNotFound() {
		AuthorDTO dto = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<AuthorDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<AuthorDTO> responseEntity = rest.exchange("/author/1", HttpMethod.PUT, requestEntity, AuthorDTO.class);
		assertThat(dto).isNull();
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
}
