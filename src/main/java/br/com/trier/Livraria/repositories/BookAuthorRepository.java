package br.com.trier.Livraria.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.Livraria.domain.Author;
import br.com.trier.Livraria.domain.Book;
import br.com.trier.Livraria.domain.BookAuthor;


@Repository
public interface BookAuthorRepository extends JpaRepository<BookAuthor, Integer>{

	List<BookAuthor> findByBook(Book book);
	List<BookAuthor> findByAuthor(Author author);
	   
}
