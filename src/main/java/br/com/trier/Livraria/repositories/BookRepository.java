package br.com.trier.Livraria.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.Livraria.domain.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{

	Optional<Book> findByTitleIgnoreCase(String title);
	Optional<Book> findByPrice(Integer price);
	
}
