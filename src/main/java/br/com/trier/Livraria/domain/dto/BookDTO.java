package br.com.trier.Livraria.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
	
	private Integer id;
	private String title;
	private Integer price;

}
