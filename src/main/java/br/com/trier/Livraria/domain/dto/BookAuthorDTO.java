package br.com.trier.Livraria.domain.dto;

import br.com.trier.Livraria.domain.Author;
import br.com.trier.Livraria.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookAuthorDTO {
	
	private Integer id;
	private Book book;
	private Author author;

}
