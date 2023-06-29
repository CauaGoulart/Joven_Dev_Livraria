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
import br.com.trier.Livraria.domain.Genres;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/genres.sql")
@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:/resources/sqls/clearTable.sql")
@SpringBootTest(classes = LivrariaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GenresResourceTest {

    @Autowired
    protected TestRestTemplate rest;

    private ResponseEntity<Genres> getGenres(String url) {
        return rest.getForEntity(url, Genres.class);
    }

    private ResponseEntity<List<Genres>> getGenresList(String url) {
        return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Genres>>() {
        });
    }

    @Test
    @DisplayName("Buscar por ID")
    public void testFindById() {
        ResponseEntity<Genres> response = getGenres("/genres/1");
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        Genres genres = response.getBody();
        assertEquals("Genres 1", genres.getGenres());
    }

    @Test
    @DisplayName("Buscar por nome do gênero")
    public void testFindByGenresIgnoreCase() {
        ResponseEntity<List<Genres>> response = getGenresList("/genres/name/fiction");
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        List<Genres> genresList = response.getBody();
        assertThat(genresList).isNotNull();
        assertThat(genresList).hasSize(1);
        assertEquals("Fiction", genresList.get(0).getGenres());
    }

    @Test
    @DisplayName("Listar todos os gêneros")
    public void testListAllGenres() {
        ResponseEntity<List<Genres>> response = getGenresList("/genres");
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        List<Genres> genresList = response.getBody();
        assertThat(genresList).isNotNull();
        assertThat(genresList).hasSize(2);
    }

    @Test
    @DisplayName("Inserir um novo gênero")
    public void testInsertGenres() {
        Genres genres = new Genres();
        genres.setGenres("Horror");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Genres> requestEntity = new HttpEntity<>(genres, headers);
        ResponseEntity<Genres> responseEntity = rest.exchange("/genres", HttpMethod.POST, requestEntity, Genres.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Genres insertedGenres = responseEntity.getBody();
        assertThat(insertedGenres).isNotNull();
        assertEquals("Horror", insertedGenres.getGenres());
    }

    @Test
    @DisplayName("Atualizar um gênero existente")
    public void testUpdateGenres() {
        ResponseEntity<Genres> response = getGenres("/genres/1");
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        Genres genres = response.getBody();
        assertThat(genres).isNotNull();
        genres.setGenres("Updated Genre");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Genres> requestEntity = new HttpEntity<>(genres, headers);
        ResponseEntity<Genres> responseEntity = rest.exchange("/genres/1", HttpMethod.PUT, requestEntity, Genres.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Genres updatedGenres = responseEntity.getBody();
        assertThat(updatedGenres).isNotNull();
        assertEquals("Updated Genre", updatedGenres.getGenres());
    }

    @Test
    @DisplayName("Excluir um gênero existente")
    public void testDeleteGenres() {
        ResponseEntity<Void> response = rest.exchange("/genres/1", HttpMethod.DELETE, null, Void.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}

