package br.com.trier.Livraria.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.Livraria.BaseTest;
import br.com.trier.Livraria.domain.Author;
import br.com.trier.Livraria.domain.Genres;
import br.com.trier.Livraria.domain.dto.ReportBookAuthorDTO;
import br.com.trier.Livraria.domain.dto.ReportBookGenresDTO;

public class ReportServiceTest extends BaseTest{

	 @Autowired
	 private ReportService service;
	
	 @Autowired
	 private BookAuthorService bookAuthorService;

	 @Autowired
	 private BookGenresService bookGenresService;
	@Test
	@DisplayName("Teste listar livros de um autor")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/bookAuthor.sql"})

	    public void testGetBooksByAuthor() {
		Author author = bookAuthorService.findById(1).getAuthor();
		List<ReportBookAuthorDTO> result = service.getBooksByAuthor(author);
        assertEquals(1, result.size());
	    }

	@Test
	@DisplayName("Teste listar todos os livros de um genero")
	@Sql({"classpath:/resources/sqls/clearTable.sql"})
	@Sql({"classpath:/resources/sqls/bookGenres.sql"})
	    public void testGetBooksByGenre() {
		Genres genres = bookGenresService.findById(1).getGenres();
		List<ReportBookGenresDTO> result = service.getBooksByGenre(genres);
        assertEquals(1, result.size());
	    }

}
