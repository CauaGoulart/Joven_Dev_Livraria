package br.com.trier.Livraria.domain.dto;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaleDTO {
	
	private Integer id;
	private ZonedDateTime date;
    private Integer userId;

}
