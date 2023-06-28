package br.com.trier.Livraria.services;

import java.util.List;
import java.util.Optional;

import br.com.trier.Livraria.domain.Author;

public interface AuthorService {

	Author insert (Author author);
	List<Author> listAll();
	Author findById(Integer id);
	Optional<Author> findByNameIgnoreCase(String name);
	Optional<Author> findByGenderIgnoreCase(String gender);
    Author update(Author author);
    void delete(Integer id);
	
}
