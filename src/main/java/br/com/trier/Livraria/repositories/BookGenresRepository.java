package br.com.trier.Livraria.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.Livraria.domain.Book;
import br.com.trier.Livraria.domain.BookGenres;
import br.com.trier.Livraria.domain.Genres;

@Repository
public interface BookGenresRepository extends JpaRepository<BookGenres, Integer>{

	List<BookGenres> findByBookIgnoreCase(Book book);
	List<BookGenres> findByGenresIgnoreCase(Genres genres);
	List<BookGenres> findByGenresAndBookIgnoreCase(Genres genres,Book book);
	
}
