package br.com.trier.Livraria.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.Livraria.BaseTest;
import br.com.trier.Livraria.domain.Client;
import br.com.trier.Livraria.domain.Sale;
import br.com.trier.Livraria.services.exceptions.ObjectNotFound;

public class SaleServiceTest extends BaseTest{
	
	@Autowired
	SaleService service;
	
	@Autowired
	ClientService clientService;
	
	@Test
	@DisplayName("Teste buscar venda por id")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/sale.sql"})
	void findByIdTest() {
		var venda = service.findById(2);
		assertThat(venda).isNotNull();
		assertEquals(2, venda.getId());
		
	}
	
	@Test
	@DisplayName("Teste buscar venda por id inexistente")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/sale.sql"})
	void findByIdInexistente() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("Venda com id 10 não encontrada.", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste listar todos")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/sale.sql"})
	void listAll() {
		assertEquals(2, service.listAll().size());
	}
	
	@Test
	@DisplayName("Teste listar todos nulo")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	void listAllNull() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.listAll());
		assertEquals("Nenhuma venda registrada.", exception.getMessage());

	}
	
	@Test
	@DisplayName("Teste cadastrar venda")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	void insert() {
		Client client = new Client(null,"exemplo","exemplo","exemplo");
		clientService.insert(client);
		LocalDateTime date =  LocalDateTime.now();
		Sale venda = service.insert(new Sale(null,date,client));
		assertThat(venda).isNotNull();
		assertEquals(1, venda.getId());

	}
	
	@Test
	@DisplayName("Teste update no venda")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/sale.sql"})
	void updateSale() {
		LocalDateTime date =  LocalDateTime.now();
	    Sale vendaUpdated =service.update( new Sale(2, date, null));
	    Sale vendaSearch = service.findById(vendaUpdated.getId());
	    assertEquals(vendaUpdated.getId(), vendaSearch.getId());
	}
	
	@Test
	@DisplayName("Teste deletar venda")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/sale.sql"})
	void deleteSale() {
		service.delete(1);
		List<Sale> lista = service.listAll();
		assertEquals(1, lista.size());
	}
	
	@Test
	@DisplayName("Teste deletar venda inexistente")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/sale.sql"})
	void deleteSaleInexistente() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.delete(10));
		assertEquals("Venda com id 10 não encontrada.", exception.getMessage());
		List<Sale> lista = service.listAll();
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName("Teste buscar venda por client")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/sale.sql"})
	void findByClientTest() {
	    Client client = clientService.findById(1);
	    List<Sale> lista = service.findByClient(client);
	    assertEquals(1, lista.size());
	}
	
	@Test
	@DisplayName("Teste buscar venda por cliente inexistente")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/sale.sql"})
	void findByClientTestInexistente() {
		Client client = new Client();
	    client.setId(100);
	    var exception = assertThrows(ObjectNotFound.class, () -> service.findByClient(client));
	    assertEquals("Nenhum cliente encontrado: %s.".formatted(client), exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar venda por data")
	@Sql({ "classpath:/resources/sqls/clearTable.sql", "classpath:/resources/sqls/sale.sql" })
	void findByDate() {
	    LocalDateTime date = LocalDateTime.of(2023, 6, 20, 15, 30);
	    Optional<Sale> venda = service.findByDate(date);
	    assertEquals(date, venda.get().getDate());
	}

	
	@Test
	@DisplayName("Teste buscar venda por data inexistente")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/sale.sql"})
	void findByNumberInexistant() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByDate(null));
		assertEquals("Nenhuma venda nesta data: null.", exception.getMessage());

	}

}
