package br.com.trier.Livraria.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.Livraria.domain.Book;
import br.com.trier.Livraria.repositories.BookRepository;
import br.com.trier.Livraria.services.BookService;
import br.com.trier.Livraria.services.exceptions.ObjectNotFound;

@Service
public class BookServiceImpl implements BookService{
	
	@Autowired
	BookRepository repository;

	@Override
	public Book insert(Book livro) {
		return repository.save(livro);
	}

	@Override
	public List<Book> listAll() {
		return repository.findAll();
	}

	@Override
	public Book findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("Livro id %s não existe".formatted(id)));
	}

	@Override
	public Book update(Book livro) {
		return repository.save(livro);
	}

	@Override
	public void delete(Integer id) {
		Book livro = findById(id);
		repository.delete(livro);
	}

	@Override
	public Optional<Book> findByTitleIgnoreCase(String title) {
		Optional<Book> list = repository.findByTitleIgnoreCase(title);
		if (list.isEmpty()) {
			throw new ObjectNotFound("No book name starts with %s." .formatted(title));
		}
		return repository.findByTitleIgnoreCase(title);

	}

	@Override
	public Optional<Book> findByPrice(Integer price) {
		Optional<Book> list = repository.findByPrice(price);
		if (list.isEmpty()) {
			throw new ObjectNotFound("No book that has that price: %s." .formatted(price));
		}
		return repository.findByPrice(price);
	}

}
