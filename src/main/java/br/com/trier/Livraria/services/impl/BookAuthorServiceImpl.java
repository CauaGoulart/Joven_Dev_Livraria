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
	public BookAuthor insert(BookAuthor piloto) {
		
		return repository.save(piloto);
	}

	@Override
	public List<BookAuthor> listAll() {
		List<BookAuthor> list = repository.findAll();
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhum piloto cadastrado");
		}
		
		return list;
	}

	@Override
	public BookAuthor findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("BookAuthor com id %s não existe".formatted(id)));

	}

	@Override
	public List<BookAuthor> findByBook(Book book) {
		List<BookAuthor> list = repository.findByBook(book);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhum piloto no país %s".formatted(book));
		}
		
		return list;
	}

	@Override
	public List<BookAuthor> findByAuthor(Author author) {
		List<BookAuthor> list = repository.findByAuthor(author);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhum piloto na equipe %s".formatted(author));
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
		BookAuthor piloto = findById(id);
		repository.delete(piloto);
		
	}

}
