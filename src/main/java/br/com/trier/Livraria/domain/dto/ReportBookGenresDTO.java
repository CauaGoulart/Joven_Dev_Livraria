package br.com.trier.Livraria.domain.dto;

import br.com.trier.Livraria.domain.Book;
import br.com.trier.Livraria.domain.Genres;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReportBookGenresDTO {
	
	private Integer idGenres;
	private Book book;
	private Genres genres;

}
