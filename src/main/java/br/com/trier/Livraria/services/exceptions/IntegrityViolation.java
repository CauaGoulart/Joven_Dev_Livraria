package br.com.trier.Livraria.services.exceptions;

@SuppressWarnings("serial")
public class IntegrityViolation extends RuntimeException{

	public IntegrityViolation(String mensagem) {
		super(mensagem);
	}
}
