package br.com.trier.Livraria.services.exceptions;

@SuppressWarnings("serial")
public class ObjectNotFound extends RuntimeException{
	

	public ObjectNotFound(String mensagem) {
		super(mensagem);
	}

}
