package br.com.trier.Livraria.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.Livraria.BaseTest;
import br.com.trier.Livraria.domain.Sale;
import br.com.trier.Livraria.domain.Book;
import br.com.trier.Livraria.domain.Client;
import br.com.trier.Livraria.domain.SaleBook;
import br.com.trier.Livraria.services.exceptions.ObjectNotFound;

public class SaleBookServiceTest extends BaseTest{
	
	@Autowired
	SaleBookService service;
	
	@Autowired
	BookService bookService;
	
	@Autowired
	SaleService saleService;
	
	@Autowired
	ClientService clientService;

	@Test
	@DisplayName("Teste buscar SaleBook por id")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/saleBook.sql"})
	void findByIdTest() {
		var SaleBook = service.findById(1);
		assertThat(SaleBook).isNotNull();
		assertEquals(1, SaleBook.getId());
		
	}
	
	@Test
	@DisplayName("Teste buscar SaleBook por id inexistente")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/saleBook.sql"})
	void findByIdTestInexistente() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("Nenhum resultado com o id 10.", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste listar todos")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/saleBook.sql"})
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
	@DisplayName("Teste cadastrar SaleBook")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	void insert() {
		Book book = new Book(null,"nome",34);
		bookService.insert(book);
		Client client = new Client(null,"exemplo","exemplo","exemplo");
		clientService.insert(client);
		LocalDateTime date =  LocalDateTime.now();
		Sale sale = saleService.insert(new Sale(null,date,client));
		
		SaleBook SaleBook = new SaleBook(null,sale,book,40);
		service.insert(SaleBook);
		assertThat(SaleBook).isNotNull();
		assertEquals(1, SaleBook.getId());

	}
	
	@Test
	@DisplayName("Teste update no SaleBook")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/saleBook.sql"})
	void updateSaleBook() {
		Book book = new Book(1,"nome",34);
		bookService.update(book);
		Client client = new Client(1,"exemplo","exemplo","exemplo");
		clientService.update(client);
		LocalDateTime date =  LocalDateTime.now();
		Sale sale = saleService.update(new Sale(1,date,client));
		
		SaleBook saleBook = service.update(new SaleBook(1,sale,book,40));
		assertThat(sale).isNotNull();	
		var SaleBookTest = service.findById(1);
		assertEquals(1, SaleBookTest.getId());
		assertEquals(1, sale.getId());
		assertEquals(40, saleBook.getQt());

	}
	
	@Test
	@DisplayName("Teste deletar SaleBook")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/saleBook.sql"})
	void deleteSaleBook() {
		service.delete(2);
		List<SaleBook> lista = service.listAll();
		assertEquals(1, lista.size());
	}
	
	@Test
	@DisplayName("Teste deletar SaleBook inexistente")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/saleBook.sql"})
	void deleteSaleBookInexistente() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.delete(10));
		assertEquals("Nenhum resultado com o id 10.", exception.getMessage());
		List<SaleBook> lista = service.listAll();
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName("Teste buscar venda por quantidade")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/saleBook.sql"})
	void findByNameTest() {
		List<SaleBook> result = service.findByQt(10);
		assertEquals(1, result.size());
		
	}
	
	@Test
	@DisplayName("Teste buscar autor por nome inexistente")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/saleBook.sql"})
	void findByNameNonExistantTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByQt(999));
		assertEquals("Quantidade não encontrada: 999.", exception.getMessage());	
	}
	
	
	@Test
	@DisplayName("Teste buscar SaleBook por livro")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/saleBook.sql"})
	void findByPaisTest() {
	    Book book = bookService.findById(1);
	    List<SaleBook> lista = service.findByBook(book);
	    assertEquals(1, lista.size());
	}

	@Test
	@DisplayName("Teste buscar SaleBook por livro inexistente")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/saleBook.sql"})
	void findByPaisTestInexistente() {
		Book book = new Book();
	    book.setId(100);
	    var exception = assertThrows(ObjectNotFound.class, () -> service.findByBook(book));
	    assertEquals("Livro não encontrado: %s.".formatted(book), exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar SaleBook por sale")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/saleBook.sql"})
	void findBySaleTest() {
	    Sale sale = saleService.findById(1);
	    List<SaleBook> lista = service.findBySale(sale);
	    assertEquals(1, lista.size());
	}

	@Test
	@DisplayName("Teste buscar SaleBook por sale inexistente")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/saleBook.sql"})
	void findBySaleInexistente() {
		Sale Sale = new Sale();
		Sale.setId(100);
	    var exception = assertThrows(ObjectNotFound.class, () -> service.findBySale(Sale));
	    assertEquals("Venda não encontrada: %s.".formatted(Sale), exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar SaleBook por livro e sale")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/saleBook.sql"})
	void findByBookAndSale() {
		Book book = bookService.findById(1);
		Sale sale = saleService.findById(1);
	    List<SaleBook> SaleBook = service.findByBookAndSale(book,sale);
	    assertEquals(1, SaleBook.size());
	}
	
	@Test
	@DisplayName("Teste buscar phone por livro e sale inexistentes")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/saleBook.sql"})
	void findByClientAndNumberInexistant() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByBookAndSale(null,null));
		assertEquals("Nenhum resultado para esta pesquisa.", exception.getMessage());

	}

}

