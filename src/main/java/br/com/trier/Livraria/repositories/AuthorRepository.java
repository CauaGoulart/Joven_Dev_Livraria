package br.com.trier.Livraria.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.Livraria.domain.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer>{
	
	Optional<Author> findByNameIgnoreCase(String name);
	Optional<Author> findByGenderIgnoreCase(String gender);

}
