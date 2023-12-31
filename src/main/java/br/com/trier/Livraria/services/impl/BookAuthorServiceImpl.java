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
			throw new ObjectNotFound("Nenhum registro encontrado.");
		}
		
		return list;
	}

	@Override
	public BookAuthor findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("Nenhum resultado com o id %s.".formatted(id)));

	}

	@Override
	public List<BookAuthor> findByBook(Book book) {
		List<BookAuthor> list = repository.findByBook(book);
		if(list.size() == 0) {
			throw new ObjectNotFound("Livro não encontrado: %s.".formatted(book));
		}
		
		return list;
	}

	@Override
	public List<BookAuthor> findByAuthor(Author author) {
		List<BookAuthor> list = repository.findByAuthor(author);
		if(list.size() == 0) {
			throw new ObjectNotFound("Autor não encontrado: %s.".formatted(author));
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
	public List<BookAuthor> findByBookAndAuthor(Book book, Author author) {
		List<BookAuthor> list = repository.findByBookAndAuthor(book,author);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhum resultado para esta pesquisa.");
		}
		
		return list;
	}

}
