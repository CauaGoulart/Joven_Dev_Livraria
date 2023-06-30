package br.com.trier.Livraria.services.impl;

import br.com.trier.Livraria.domain.Author;
import br.com.trier.Livraria.domain.Book;
import br.com.trier.Livraria.domain.BookAuthor;
import br.com.trier.Livraria.domain.BookGenres;
import br.com.trier.Livraria.domain.Genres;
import br.com.trier.Livraria.domain.dto.ReportBookAuthorDTO;
import br.com.trier.Livraria.domain.dto.ReportBookGenresDTO;
import br.com.trier.Livraria.services.BookAuthorService;
import br.com.trier.Livraria.services.BookGenresService;
import br.com.trier.Livraria.services.ReportService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

	 private final BookAuthorService bookAuthorService;
	    private final BookGenresService bookGenresService;

	    @Autowired
	    public ReportServiceImpl(BookAuthorService bookAuthorService, BookGenresService bookGenresService) {
	        this.bookAuthorService = bookAuthorService;
	        this.bookGenresService = bookGenresService;
	    }

	    @Override
	    public List<ReportBookAuthorDTO> getBooksByAuthor(Author author) {
	        List<BookAuthor> bookAuthors = bookAuthorService.findByAuthor(author);
	        List<ReportBookAuthorDTO> result = new ArrayList<>();
	        for (BookAuthor bookAuthor : bookAuthors) {
	            Book book = bookAuthor.getBook();
	            result.add(new ReportBookAuthorDTO(author.getId(), book, author));
	        }
	        return result;
	    }

	    @Override
	    public List<ReportBookGenresDTO> getBooksByGenre(Genres genre) {
	        List<BookGenres> bookGenres = bookGenresService.findByGenres(genre);
	        List<ReportBookGenresDTO> result = new ArrayList<>();
	        for (BookGenres bookGenre : bookGenres) {
	            Book book = bookGenre.getBook();
	            Genres genres = bookGenre.getGenres();
	            result.add(new ReportBookGenresDTO(genres.getId(), book, genres));
	        }
	        return result;
	    }

}
