package br.com.trier.Livraria.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
import br.com.trier.Livraria.domain.dto.ClientDTO;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@SpringBootTest(classes = LivrariaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientResourceTest {

    @Autowired
    protected TestRestTemplate rest;

    private ResponseEntity<ClientDTO> getClient(String url) {
        return rest.getForEntity(url, ClientDTO.class);
    }

    @SuppressWarnings("unused")
	private ResponseEntity<List<ClientDTO>> getClients(String url) {
        return rest.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ClientDTO>>() {
                });
    }

    @Test
    @DisplayName("Buscar por id")
    @Sql(scripts = "classpath:/resources/sqls/clearTable.sql")
    @Sql(scripts = "classpath:/resources/sqls/client.sql")
    public void testGetOk() {
        ResponseEntity<ClientDTO> response = getClient("/client/1");
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ClientDTO client = response.getBody();
        assertEquals("test 1", client.getName());
    }

    @Test
    @DisplayName("Buscar por id inexistente")
    @Sql(scripts = "classpath:/resources/sqls/clearTable.sql")
    @Sql(scripts = "classpath:/resources/sqls/client.sql")
    public void testGetNotFound() {
        ResponseEntity<ClientDTO> response = getClient("/client/300");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Cadastrar usuário")
    @Sql(scripts = "classpath:/resources/sqls/clearTable.sql")
    public void testCreateClient() {
        ClientDTO dto = new ClientDTO(null, "nome", "email", "senha");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ClientDTO> requestEntity = new HttpEntity<>(dto, headers);
        ResponseEntity<ClientDTO> responseEntity = rest.exchange(
                "/client",
                HttpMethod.POST,
                requestEntity,
                ClientDTO.class
        );
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ClientDTO client = responseEntity.getBody();
        assertEquals("nome", client.getName());
    }

    @Test
    @DisplayName("Atualizar usuário")
    @Sql(scripts = "classpath:/resources/sqls/clearTable.sql")
    @Sql(scripts = "classpath:/resources/sqls/client.sql")
    public void testUpdateClient() {
        ClientDTO dto = new ClientDTO(null, "Novo Nome", "novonome@teste.com", "novasenha");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ClientDTO> requestEntity = new HttpEntity<>(dto, headers);
        ResponseEntity<ClientDTO> responseEntity = rest.exchange("/client/1", HttpMethod.PUT, requestEntity, ClientDTO.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ClientDTO updatedClient = responseEntity.getBody();
        assertThat(updatedClient).isNotNull();
        assertEquals("Novo Nome", updatedClient.getName());
        assertEquals("novonome@teste.com", updatedClient.getEmail());
    }

    @Test
    @DisplayName("Excluir usuário")
    @Sql(scripts = "classpath:/resources/sqls/clearTable.sql")
    @Sql(scripts = "classpath:/resources/sqls/client.sql")
    public void testDeleteClient() {
        ResponseEntity<Void> responseEntity = rest.exchange("/client/1", HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ResponseEntity<ClientDTO> getClientResponse = getClient("/client/1");
        assertEquals(HttpStatus.NOT_FOUND, getClientResponse.getStatusCode());
    }

    @Test
    @DisplayName("Buscar usuários por nome")
    @Sql(scripts = "classpath:/resources/sqls/clearTable.sql")
    @Sql(scripts = "classpath:/resources/sqls/client.sql")
    public void testGetClientsByName() {
        ResponseEntity<List<ClientDTO>> responseEntity = rest.exchange("/client/name/Usuario teste 1", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ClientDTO>>() {
                });
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<ClientDTO> clientList = responseEntity.getBody();
        assertNotNull(clientList);
        assertThat(clientList).extracting(ClientDTO::getName).containsOnly("Usuario teste 1");
    }

    @Test
    @DisplayName("Listar todos os usuários")
    @Sql(scripts = "classpath:/resources/sqls/clearTable.sql")
    @Sql(scripts = "classpath:/resources/sqls/client.sql")
    public void testGetAllClients() {
        ResponseEntity<List<ClientDTO>> responseEntity = rest.exchange("/client", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ClientDTO>>() {
                });
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<ClientDTO> clientList = responseEntity.getBody();
        assertNotNull(clientList);
        assertEquals(2, clientList.size());
    }

    @Test
    @DisplayName("Cadastrar usuário - BadRequest")
    @Sql(scripts = "classpath:/resources/sqls/clearTable.sql")
    public void testCreateClientBadRequest() {
        ClientDTO dto = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ClientDTO> requestEntity = new HttpEntity<>(dto, headers);
        ResponseEntity<ClientDTO> responseEntity = rest.exchange("/client", HttpMethod.POST, requestEntity, ClientDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Atualizar usuário - BadRequest")
    @Sql(scripts = "classpath:/resources/sqls/clearTable.sql")
    @Sql(scripts = "classpath:/resources/sqls/client.sql")
    public void testUpdateClientBadRequest() {
        ClientDTO dto = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ClientDTO> requestEntity = new HttpEntity<>(dto, headers);
        ResponseEntity<ClientDTO> responseEntity = rest.exchange("/client/1", HttpMethod.PUT, requestEntity, ClientDTO.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Atualizar usuário - Objeto não encontrado")
    @Sql(scripts = "classpath:/resources/sqls/clearTable.sql")
    public void testUpdateClientNotFound() {
        ClientDTO dto = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ClientDTO> requestEntity = new HttpEntity<>(dto, headers);
        ResponseEntity<ClientDTO> responseEntity = rest.exchange("/client/1", HttpMethod.PUT, requestEntity, ClientDTO.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}
