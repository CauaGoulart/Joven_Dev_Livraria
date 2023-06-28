package br.com.trier.Livraria.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {

	private Integer id;
	private String name;
	private String gender;
}
