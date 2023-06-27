package br.com.trier.Livraria.services;

import java.util.List;
import java.util.Optional;

import br.com.trier.Livraria.domain.Book;


public interface BookService {

	Book insert (Book book);
	List<Book> listAll();
	Book findById(Integer id);
	Optional<Book> findByTitleIgnoreCase(String title);
	Optional<Book> findByPrice(Integer price);
    Book update(Book book);
    void delete(Integer id);
}
