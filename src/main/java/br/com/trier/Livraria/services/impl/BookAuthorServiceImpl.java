package br.com.trier.Livraria.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.Livraria.domain.Author;
import br.com.trier.Livraria.domain.Book;
import br.com.trier.Livraria.domain.BookAuthor;
import br.com.trier.Livraria.repositories.BookAuthorRepository;
import br.com.trier.Livraria.services.BookAuthorService;
import br.com.trier.Livraria.services.exceptions.ObjectNotFound;

@Service
public class BookAuthorServiceImpl implements BookAuthorService{

	@Autowired
	private BookAuthorRepository repository;

	@Override
	public BookAuthor insert(BookAuthor bookAuthor) {
		
		return repository.save(bookAuthor);
	}

	@Override
	public List<BookAuthor> listAll() {
		List<BookAuthor> list = repository.findAll();
		if(list.size() == 0) {
			throw new ObjectNotFound("No bookAuthor registered.");
		}
		
		return list;
	}

	@Override
	public BookAuthor findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("BookAuthor with id %s does not exist.".formatted(id)));

	}

	@Override
	public List<BookAuthor> findByBookIgnoreCase(Book book) {
		List<BookAuthor> list = repository.findByBookIgnoreCase(book);
		if(list.size() == 0) {
			throw new ObjectNotFound("No book found: %s.".formatted(book));
		}
		
		return list;
	}

	@Override
	public List<BookAuthor> findByAuthorIgnoreCase(Author author) {
		List<BookAuthor> list = repository.findByAuthorIgnoreCase(author);
		if(list.size() == 0) {
			throw new ObjectNotFound("No author found: %s.".formatted(author));
		}
		
		return list;
	}

	@Override
	public BookAuthor update(BookAuthor bookAuthor) {
		findById(bookAuthor.getId());
		return repository.save(bookAuthor);
	}

	@Override
	public void delete(Integer id) {
		BookAuthor bookAuthor = findById(id);
		repository.delete(bookAuthor);
		
	}

	@Override
	public List<BookAuthor> findByBookAndAuthorIgnoreCase(Book book, Author author) {
		List<BookAuthor> list = repository.findByBookAndAuthorIgnoreCase(book,author);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nothing found for this search: %s.".formatted(book,author));
		}
		
		return list;
	}

}
