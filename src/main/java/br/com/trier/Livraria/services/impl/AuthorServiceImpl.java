package br.com.trier.Livraria.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.Livraria.domain.Author;
import br.com.trier.Livraria.repositories.AuthorRepository;
import br.com.trier.Livraria.services.AuthorService;
import br.com.trier.Livraria.services.exceptions.ObjectNotFound;

@Service
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
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("Autor com id %s não foi encontrado.".formatted(id)));
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
			throw new ObjectNotFound("Nenhum autor com o nome %s encontrado" .formatted(name));
		}
		return repository.findByNameIgnoreCase(name);

	}
	
	@Override
	public Optional<Author> findByGenderIgnoreCase(String gender) {
		Optional<Author> lista = repository.findByGenderIgnoreCase(gender);
		if (lista.isEmpty()) {
			throw new ObjectNotFound("Nenhum autor com o gênero %s encontrado" .formatted(gender));
		}
		return repository.findByGenderIgnoreCase(gender);

	}

}
