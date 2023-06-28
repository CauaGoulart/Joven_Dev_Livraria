package br.com.trier.Livraria.services;

import java.util.List;

import br.com.trier.Livraria.domain.Author;
import br.com.trier.Livraria.domain.Book;
import br.com.trier.Livraria.domain.BookAuthor;



public interface BookAuthorService {

	BookAuthor insert (BookAuthor pista );
	List<BookAuthor> listAll();
	BookAuthor findById(Integer id);
	List<BookAuthor> findByBook(Book book);
	List<BookAuthor> findByAuthor(Author author);
	List<BookAuthor> findByBookAndAuthor(Book book,Author author);
	BookAuthor update(BookAuthor bookAuthor);
    void delete(Integer id);
    
}
