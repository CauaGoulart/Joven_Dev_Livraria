package br.com.trier.Livraria.services.exceptions;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StandardError {
	
	private LocalDateTime time;
	private Integer status;
	private String erro;
	private String url;
	

}
