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
			throw new ObjectNotFound("Nenhum registro encontrado.");
		}
		
		return list;
	}

	@Override
	public BookGenres findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("Nenhum resultado com o id %s.".formatted(id)));

	}

	@Override
	public List<BookGenres> findByBook(Book book) {
		List<BookGenres> list = repository.findByBook(book);
		if(list.size() == 0) {
			throw new ObjectNotFound("Livro não encontrado: %s.".formatted(book));
		}
		
		return list;
	}

	@Override
	public List<BookGenres> findByGenres(Genres genres) {
		List<BookGenres> list = repository.findByGenres(genres);
		if(list.size() == 0) {
			throw new ObjectNotFound("Gênero não encontrado: %s.".formatted(genres));
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
	public List<BookGenres> findByGenresAndBook(Genres genres, Book book) {
		List<BookGenres> list = repository.findByGenresAndBook(genres,book);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhum resultado para esta pesquisa.");
		}
		
		return list;
	}

}
