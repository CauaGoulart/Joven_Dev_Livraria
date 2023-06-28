package br.com.trier.Livraria.domain.dto;

import br.com.trier.Livraria.domain.Book;
import br.com.trier.Livraria.domain.Sale;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaleBookDTO {
	
	private Integer id;
	private Sale sale;
	private Book book;

}
