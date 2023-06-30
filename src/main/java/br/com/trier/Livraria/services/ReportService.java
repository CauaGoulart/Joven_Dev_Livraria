package br.com.trier.Livraria.services;

import br.com.trier.Livraria.domain.Author;
import br.com.trier.Livraria.domain.Genres;
import br.com.trier.Livraria.domain.dto.ReportBookAuthorDTO;
import br.com.trier.Livraria.domain.dto.ReportBookGenresDTO;

import java.util.List;


public interface ReportService {

	 List<ReportBookAuthorDTO> getBooksByAuthor(Author author);
	 List<ReportBookGenresDTO> getBooksByGenre(Genres genres);
}
