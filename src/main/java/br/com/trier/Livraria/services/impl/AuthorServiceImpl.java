package br.com.trier.Livraria.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.trier.Livraria.domain.Author;
import br.com.trier.Livraria.repositories.AuthorRepository;
import br.com.trier.Livraria.services.AuthorService;
import br.com.trier.Livraria.services.exceptions.ObjectNotFound;

public class AuthorServiceImpl implements AuthorService{
	
	@Autowired
	AuthorRepository repository;

	@Override
	public Author insert(Author author) {
		return repository.save(author);
	}

	@Override
	public List<Author> listAll() {
		return repository.findAll();
	}

	@Override
	public Author findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("Livro id %s não existe".formatted(id)));
	}

	@Override
	public Author update(Author author) {
		return repository.save(author);
	}

	@Override
	public void delete(Integer id) {
		Author author = findById(id);
		repository.delete(author);
	}

	@Override
	public Optional<Author> findByNameIgnoreCase(String name) {
		Optional<Author> lista = repository.findByNameIgnoreCase(name);
		if (lista.isEmpty()) {
			throw new ObjectNotFound("No author name starts with %s." .formatted(name));
		}
		return repository.findByNameIgnoreCase(name);

	}
	
	@Override
	public Optional<Author> findByGenderIgnoreCase(String gender) {
		Optional<Author> lista = repository.findByNameIgnoreCase(gender);
		if (lista.isEmpty()) {
			throw new ObjectNotFound("No author gender with the gender %s." .formatted(gender));
		}
		return repository.findByNameIgnoreCase(gender);

	}

}
