package br.com.trier.Livraria;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import br.com.trier.Livraria.services.AuthorService;
import br.com.trier.Livraria.services.BookAuthorService;
import br.com.trier.Livraria.services.BookGenresService;
import br.com.trier.Livraria.services.BookService;
import br.com.trier.Livraria.services.ClientService;
import br.com.trier.Livraria.services.GenresService;
import br.com.trier.Livraria.services.ReportService;
import br.com.trier.Livraria.services.SaleBookService;
import br.com.trier.Livraria.services.SaleService;
import br.com.trier.Livraria.services.impl.AuthorServiceImpl;
import br.com.trier.Livraria.services.impl.BookAuthorServiceImpl;
import br.com.trier.Livraria.services.impl.BookGenresServiceImpl;
import br.com.trier.Livraria.services.impl.BookServiceImpl;
import br.com.trier.Livraria.services.impl.ClientServiceImpl;
import br.com.trier.Livraria.services.impl.GenresServiceImpl;
import br.com.trier.Livraria.services.impl.ReportServiceImpl;
import br.com.trier.Livraria.services.impl.SaleBookServiceImpl;
import br.com.trier.Livraria.services.impl.SaleServiceImpl;


@TestConfiguration
@SpringBootTest
@ActiveProfiles("test")
public class BaseTest {
	
	@Bean
	public ClientService clientService() {
		return new ClientServiceImpl();
	}
	
	@Bean
	public GenresService genresService() {
		return new GenresServiceImpl();
	}
	
	@Bean
	public AuthorService authorService() {
		return new AuthorServiceImpl();
	}
	
	@Bean
	public BookService bookService() {
		return new BookServiceImpl();
	}
	
	@Bean
	public BookAuthorService bookAuthorService() {
		return new BookAuthorServiceImpl();
	}
	
	@Bean
	public BookGenresService bookGenresService() {
		return new BookGenresServiceImpl();
	}
	
	@Bean
	public SaleService saleService() {
		return new SaleServiceImpl();
	}
	
	@Bean
	public SaleBookService salebookService() {
		return new SaleBookServiceImpl();
	}
	
	@Bean
	public ReportService reportService() {
		return new ReportServiceImpl(null, null);
	}
}
