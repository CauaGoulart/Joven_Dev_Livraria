package br.com.trier.Livraria.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.Livraria.domain.Book;
import br.com.trier.Livraria.domain.BookGenres;
import br.com.trier.Livraria.domain.Genres;
import br.com.trier.Livraria.repositories.BookGenresRepository;
import br.com.trier.Livraria.services.BookGenresService;
import br.com.trier.Livraria.services.exceptions.ObjectNotFound;

@Service
public class BookGenresServiceImpl implements BookGenresService{
	
	@Autowired
	private BookGenresRepository repository;

	@Override
	public BookGenres insert(BookGenres bookGenres) {
		
		return repository.save(bookGenres);
	}

	@Override
	public List<BookGenres> listAll() {
		List<BookGenres> list = repository.findAll();
		if(list.size() == 0) {
			throw new ObjectNotFound("No bookGenres registered.");
		}
		
		return list;
	}

	@Override
	public BookGenres findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("BookGenres with id %s does not exist.".formatted(id)));

	}

	@Override
	public List<BookGenres> findByBookIgnoreCase(Book book) {
		List<BookGenres> list = repository.findByBookIgnoreCase(book);
		if(list.size() == 0) {
			throw new ObjectNotFound("No book found: %s.".formatted(book));
		}
		
		return list;
	}

	@Override
	public List<BookGenres> findByGenresIgnoreCase(Genres genres) {
		List<BookGenres> list = repository.findByGenresIgnoreCase(genres);
		if(list.size() == 0) {
			throw new ObjectNotFound("No genres found: %s.".formatted(genres));
		}
		
		return list;
	}

	@Override
	public BookGenres update(BookGenres bookGenres) {
		findById(bookGenres.getId());
		return repository.save(bookGenres);
	}

	@Override
	public void delete(Integer id) {
		BookGenres bookGenres = findById(id);
		repository.delete(bookGenres);
		
	}

	@Override
	public List<BookGenres> findByGenresAndBookIgnoreCase(Genres genres, Book book) {
		List<BookGenres> list = repository.findByGenresAndBookIgnoreCase(genres,book);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nothing found for this search: %s.".formatted(genres,book));
		}
		
		return list;
	}

}
