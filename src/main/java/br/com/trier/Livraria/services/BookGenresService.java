package br.com.trier.Livraria.services;

import java.util.List;

import br.com.trier.Livraria.domain.Book;
import br.com.trier.Livraria.domain.BookGenres;
import br.com.trier.Livraria.domain.Genres;

public interface BookGenresService {
	
	BookGenres insert(BookGenres bookGenres);
	List<BookGenres> listAll();
	BookGenres findById(Integer id);
	List<BookGenres> findByBookIgnoreCase(Book book);
	List<BookGenres> findByGenresIgnoreCase(Genres genres);
	List<BookGenres> findByGenresAndBookIgnoreCase(Genres genres,Book book);
	BookGenres update(BookGenres bookGenres);
    void delete(Integer id);

}
