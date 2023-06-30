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
import br.com.trier.Livraria.domain.Client;
import br.com.trier.Livraria.services.exceptions.IntegrityViolation;
import br.com.trier.Livraria.services.exceptions.ObjectNotFound;

public class ClientServiceTest extends BaseTest{
	
	@Autowired
	private ClientService clientService;

	@Test
	@DisplayName("Teste buscar client por id")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void findByIdTest() {
		var client = clientService.findById(1);
		assertThat(client).isNotNull();
		assertEquals(1, client.getId());
		assertEquals("test 1", client.getName());
		assertEquals("test1@gmail.com", client.getEmail());
		assertEquals("123", client.getPassword());
	}
	
	@Test
	@DisplayName("Teste buscar client por id inexistente")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void findByIdTestInexistente() {
		var exception = assertThrows(ObjectNotFound.class, () -> clientService.findById(10));
		assertEquals("Cliente 10 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste listr todos")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void listAll() {
		var list = clientService.listAll();
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste cadastrar client")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	void insert() {
		Client client = clientService.insert(new Client(2, "test", "test123@gmail.com", "123", "ADMIN"));
		assertThat(client).isNotNull();
		assertEquals(2, client.getId());
		assertEquals("test", client.getName());
		assertEquals("test123@gmail.com", client.getEmail());
		assertEquals("123", client.getPassword());
	}
	
	@Test
	@DisplayName("Teste cadastrar client com e-mail duplicado")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void insertExistente() {
		var exception = assertThrows(IntegrityViolation.class, () -> clientService.insert(new Client(null, "test", "test1@gmail.com", "123", "ADMIN")));
		assertEquals("Email ja cadastrado: test1@gmail.com", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste update no client")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void updateUser() {
		Client client = clientService.update(new Client(2, "testee", "test123@gmail.com", "123", "ADMIN"));
		assertThat(client).isNotNull();
		var clientTest = clientService.findById(2);
		assertEquals(2, clientTest.getId());
		assertEquals("testee", clientTest.getName());
		assertEquals("test123@gmail.com", clientTest.getEmail());
		assertEquals("123", clientTest.getPassword());
	}
	
	@Test
	@DisplayName("Update client com e-mail duplicado")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void updateUserEmailExistente() {
		var exception = assertThrows(IntegrityViolation.class, () -> clientService.update(new Client(2, "test", "test1@gmail.com", "123", "ADMIN")));
		assertEquals("Email ja cadastrado: test1@gmail.com", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar client")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void deleteUser() {
		clientService.delete(2);
		List<Client> list = clientService.listAll();
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Teste deletar client inexistente")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void deleteUserInexistente() {
		var exception = assertThrows(ObjectNotFound.class, () -> clientService.delete(10));
		assertEquals("Cliente 10 não encontrado", exception.getMessage());
		List<Client> list = clientService.listAll();
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar client por nome")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void findByName() {
		Optional<Client> result = clientService.findByNameIgnoreCase("test 2");
	    assertEquals("test 2", result.get().getName());
	}
	
	@Test
	@DisplayName("Teste buscar client por nome inexistente")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void findByNameInexistente() {
		var exception = assertThrows(ObjectNotFound.class, () -> clientService.findByNameIgnoreCase("invalid"));
		assertEquals("Nenhum cliente que possui o nome invalid", exception.getMessage());
	}

	@Test
	@DisplayName("Test find client by email")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void findByEmail() {
	    Optional<Client> result = clientService.findByEmail("test1@gmail.com");
	    assertEquals("test 1", result.get().getName());
	}

	@Test
	@DisplayName("Test find client by non-existing email")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void findByEmailNonExisting() {
	    var exception = assertThrows(ObjectNotFound.class, () -> clientService.findByEmail("invalid@gmail.com"));
	    assertEquals("Nenhum cliente com esse email: invalid@gmail.com", exception.getMessage());
	}

	@Test
	@DisplayName("Test find client by email and password")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void findByEmailAndPassword() {
	    List<Client> result = clientService.findByEmailAndPassword("test1@gmail.com", "123");
	    assertEquals(1, result.size());
	}

	@Test
	@DisplayName("Test find client by incorrect email and password")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void findByEmailAndPasswordIncorrect() {
	    var exception = assertThrows(ObjectNotFound.class, () -> clientService.findByEmailAndPassword("test1@gmail.com", "wrongpassword"));
	    assertEquals("Nenhum cliente encontrado.", exception.getMessage());
	}

	@Test
	@DisplayName("Test validate empty password")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void validateEmptyPassword() {
		  var exception = assertThrows(IntegrityViolation.class, () -> clientService.insert(new Client(null, "test", "test123@gmail.com", "", "ADMIN")));
		    assertEquals("Senha não pode estar vazia.", exception.getMessage());	}

	@Test
	@DisplayName("Test validate empty name")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void validateEmptyName() {
		   var exception = assertThrows(IntegrityViolation.class, () -> clientService.insert(new Client(null, "", "test123@gmail.com", "123", "ADMIN")));
		    assertEquals("Nome não pode estar vazio.", exception.getMessage());	
		    }

	@Test
	@DisplayName("Test validate empty email")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/client.sql"})
	void validateEmptyEmail() {
		 var exception = assertThrows(IntegrityViolation.class, () -> clientService.insert(new Client(null, "test", "", "123", "ADMIN")));
		    assertEquals("Email não pode ser vazio.", exception.getMessage());	}


}
