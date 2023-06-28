package br.com.trier.Livraria;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import br.com.trier.Livraria.services.AuthorService;
import br.com.trier.Livraria.services.BookAuthorService;
import br.com.trier.Livraria.services.BookService;
import br.com.trier.Livraria.services.GenresService;
import br.com.trier.Livraria.services.SaleService;
import br.com.trier.Livraria.services.ClientService;
import br.com.trier.Livraria.services.impl.AuthorServiceImpl;
import br.com.trier.Livraria.services.impl.BookAuthorServiceImpl;
import br.com.trier.Livraria.services.impl.BookServiceImpl;
import br.com.trier.Livraria.services.impl.GenresServiceImpl;
import br.com.trier.Livraria.services.impl.SaleServiceImpl;
import br.com.trier.Livraria.services.impl.ClientServiceImpl;


@TestConfiguration
@SpringBootTest
@ActiveProfiles("test")
public class BaseTest {
	
	@Bean
	public ClientService userService() {
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
}
