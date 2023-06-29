package br.com.trier.Livraria.services;

import br.com.trier.Livraria.domain.Author;
import br.com.trier.Livraria.domain.BookGenres;
import br.com.trier.Livraria.domain.dto.ReportBookAuthorDTO;
import java.util.List;


public interface ReportService {

	 List<ReportBookAuthorDTO> getBooksByAuthor(Author author);
	    List<BookGenres> getBooksByGenre(String genre);
}
