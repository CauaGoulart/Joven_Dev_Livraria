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
import br.com.trier.Livraria.domain.Phone;
import br.com.trier.Livraria.services.exceptions.IntegrityViolation;
import br.com.trier.Livraria.services.exceptions.ObjectNotFound;

public class PhoneServiceTest extends BaseTest{
	
	@Autowired
	PhoneService service;
	
	@Autowired
	ClientService clientService;
	

	@Test
	@DisplayName("Teste buscar phone por id")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/phone.sql"})
	void findByIdTest() {
		var phone = service.findById(5);
		assertThat(phone).isNotNull();
		assertEquals(5, phone.getId());
		
	}
	
	@Test
	@DisplayName("Teste buscar phone por id inexistente")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/phone.sql"})
	void findByIdInexistente() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("Telefone com o id 10 não existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste listar todos")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/phone.sql"})
	void listAll() {
		assertEquals(2, service.listAll().size());
	}
	
	@Test
	@DisplayName("Teste listar todos nulo")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	void listAllNull() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.listAll());
		assertEquals("Nenhum telefone cadastrado.", exception.getMessage());

	}
	
	@Test
	@DisplayName("Teste cadastrar phone")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	void insert() {
		Client client = new Client(null,"exemplo","exemplo","exemplo");
		clientService.insert(client);
		Phone phone = service.insert(new Phone(null,"99999-9999",client));
		assertThat(phone).isNotNull();
		assertEquals(1, phone.getId());

	}
	
	@Test
	@DisplayName("Teste update no phone")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/phone.sql"})
	void updatePhone() {
	    Phone phoneUpdated = new Phone(5, "88888-8888", null);
	    service.update(phoneUpdated);
	    Phone phoneSearch = service.findById(phoneUpdated.getId());
	    assertEquals(phoneUpdated.getId(), phoneSearch.getId());
	    assertEquals(phoneUpdated.getNumber(), phoneSearch.getNumber());
	}
	
	@Test
	@DisplayName("Teste deletar phone")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/phone.sql"})
	void deletePhone() {
		service.delete(6);
		List<Phone> lista = service.listAll();
		assertEquals(1, lista.size());
	}
	
	@Test
	@DisplayName("Teste deletar phone inexistente")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/phone.sql"})
	void deletePhoneInexistente() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.delete(10));
		assertEquals("Telefone com o id 10 não existe", exception.getMessage());
		List<Phone> lista = service.listAll();
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName("Teste buscar phone por client")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/phone.sql"})
	void findByClientTest() {
	    Client client = clientService.findById(5);
	    List<Phone> lista = service.findByClient(client);
	    assertEquals(1, lista.size());
	}
	
	@Test
	@DisplayName("Teste buscar phone por cliente inexistente")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/phone.sql"})
	void findByClientTestInexistente() {
		Client client = new Client();
	    client.setId(100);
	    var exception = assertThrows(ObjectNotFound.class, () -> service.findByClient(client));
	    assertEquals("Cliente não encontrado: %s.".formatted(client), exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar phone por número")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/phone.sql"})
	void findByNumber() {
	    Optional<Phone> phone = service.findByNumber("99999-9999");
	    assertEquals("99999-9999", phone.get().getNumber());
	}
	
	@Test
	@DisplayName("Teste buscar phone por número inexistente")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/phone.sql"})
	void findByNumberInexistant() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByNumber("1"));
		assertEquals("Número não encontrado: 1", exception.getMessage());

	}

	@Test
	@DisplayName("Teste buscar phone por cliente e número")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/phone.sql"})
	void findByClientAndNumber() {
		Client client = clientService.findById(5);
	    List<Phone> phone = service.findByClientAndNumber(client,"99999-9999");
	    assertEquals(1, phone.size());
	}
	
	@Test
	@DisplayName("Teste buscar phone por cliente e número inexistentes")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/phone.sql"})
	void findByClientAndNumberInexistant() {
		Client client = clientService.findById(5);
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByClientAndNumber(client,"1"));
		assertEquals("Nenhum resultado para esta pesquisa.", exception.getMessage());

	}
	
	@Test
	@DisplayName("Test validate empty number")
	@Sql({ "classpath:/resources/sqls/clearTable.sql", "classpath:/resources/sqls/client.sql" })
	void validateEmptyNumber() {
	    var exception = assertThrows(IntegrityViolation.class, () -> {
	        Client client = new Client(null, "", "test123@gmail.com", "123");
	        Phone phone = new Phone(null, "", client);
	        service.insert(phone);
	    });
	    
	    assertEquals("Número não pode estar vazio.", exception.getMessage());	
	}


}
