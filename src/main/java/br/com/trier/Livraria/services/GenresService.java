package br.com.trier.Livraria.services;

import java.util.List;
import java.util.Optional;

import br.com.trier.Livraria.domain.Genres;

public interface GenresService {

	Genres insert (Genres genres);
	List<Genres> listAll();
	Genres findById(Integer id);
	Optional<Genres> findByGenresIgnoreCase(String title);
    Genres update(Genres genres);
    void delete(Integer id);
    
}
