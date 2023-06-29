package br.com.trier.Livraria.domain.dto;

import br.com.trier.Livraria.domain.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PhoneDTO {
	
	private Integer id;
	private String number;
	private Client client;

}
