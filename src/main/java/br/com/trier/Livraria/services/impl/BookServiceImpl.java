package br.com.trier.Livraria.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.Livraria.domain.Book;
import br.com.trier.Livraria.repositories.BookRepository;
import br.com.trier.Livraria.services.BookService;
import br.com.trier.Livraria.services.exceptions.IntegrityViolation;
import br.com.trier.Livraria.services.exceptions.ObjectNotFound;

@Service
public class BookServiceImpl implements BookService{
	
	@Autowired
	BookRepository repository;
	
	private void validateBook(Book obj) {
		validateTitle(obj.getTitle());
		validatePrice(obj.getPrice());
	    
	}
	
	private void validateTitle(String tilte) {
	    if (tilte.isBlank()) {
	        throw new IntegrityViolation("Titulo não pode estar vazio.");
	    }
	}
	
	private void validatePrice(Integer price) {
	    if (price == null) {
	        throw new IntegrityViolation("Preço não pode estar vazio.");
	    }
	}

	@Override
	public Book insert(Book book) {
		validateBook(book);
		return repository.save(book);
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
			throw new ObjectNotFound("Nenhum livro com o titulo %s." .formatted(title));
		}
		return repository.findByTitleIgnoreCase(title);

	}

	@Override
	public Optional<Book> findByPrice(Integer price) {
		Optional<Book> list = repository.findByPrice(price);
		if (list.isEmpty()) {
			throw new ObjectNotFound("Nenhum livro com esse preço: %s." .formatted(price));
		}
		return repository.findByPrice(price);
	}

}
