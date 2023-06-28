package br.com.trier.Livraria.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.Livraria.domain.Genres;

@Repository
public interface GenresRepository extends JpaRepository<Genres, Integer>{

	Optional<Genres> findByGenresIgnoreCase(String genres);
	
}
