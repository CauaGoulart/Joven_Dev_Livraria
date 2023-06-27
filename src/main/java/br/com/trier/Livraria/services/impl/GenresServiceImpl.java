package br.com.trier.Livraria.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.Livraria.domain.Genres;
import br.com.trier.Livraria.repositories.GenresRepository;
import br.com.trier.Livraria.services.GenresService;
import br.com.trier.Livraria.services.exceptions.ObjectNotFound;

@Service
public class GenresServiceImpl implements GenresService{

	@Autowired
	GenresRepository repository;

	@Override
	public Genres insert(Genres livro) {
		return repository.save(livro);
	}

	@Override
	public List<Genres> listAll() {
		return repository.findAll();
	}

	@Override
	public Genres findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("BookAuthor with id %s does not exist.".formatted(id)));
	}

	@Override
	public Genres update(Genres livro) {
		return repository.save(livro);
	}

	@Override
	public void delete(Integer id) {
		Genres livro = findById(id);
		repository.delete(livro);
	}

	@Override
	public Optional<Genres> findByGenresIgnoreCase(String genres) {
		Optional<Genres> lista = repository.findByGenresIgnoreCase(genres);
		if (lista.isEmpty()) {
			throw new ObjectNotFound("No genres found: %s.".formatted(genres));
		}
		return repository.findByGenresIgnoreCase(genres);

	}
	
}
