package br.com.trier.Livraria.domain.dto;

import java.time.LocalDateTime;

import br.com.trier.Livraria.domain.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaleDTO {
	
	private Integer id;
	private LocalDateTime date;
    private Client client;

}
