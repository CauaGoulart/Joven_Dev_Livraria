package br.com.trier.Livraria.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.Livraria.domain.Author;
import br.com.trier.Livraria.domain.Genres;
import br.com.trier.Livraria.services.BookAuthorService;
import br.com.trier.Livraria.services.BookGenresService;
import br.com.trier.Livraria.services.ReportService;
import br.com.trier.Livraria.domain.dto.ReportBookAuthorDTO;
import br.com.trier.Livraria.domain.dto.ReportBookGenresDTO;

@RestController
@RequestMapping(value = "/report")
public class ReportResources {
	
	 private final ReportService reportService;
	    private final BookAuthorService bookAuthorService;
	    private final BookGenresService bookGenresService;

	    @Autowired
	    public ReportResources(ReportService reportService, BookAuthorService bookAuthorService, BookGenresService bookGenresService) {
	        this.reportService = reportService;
	        this.bookAuthorService = bookAuthorService;
	        this.bookGenresService = bookGenresService;
	    }

	    @GetMapping("/book-author/{idAuthor}")
	    public List<ReportBookAuthorDTO> getBooksByAuthor(@PathVariable Integer idAuthor) {
	        Author author = bookAuthorService.findById(idAuthor).getAuthor();
	        return reportService.getBooksByAuthor(author);
	    }

	    @GetMapping("/book-genres/{IdGenres}")
	    public List<ReportBookGenresDTO> getBooksByGenre(@PathVariable Integer IdGenres) {
	        Genres genre = bookGenresService.findById(IdGenres).getGenres();
	        return reportService.getBooksByGenre(genre);
	    }

}
