package br.com.trier.Livraria.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.trier.Livraria.domain.Book;
import br.com.trier.Livraria.domain.Sale;
import br.com.trier.Livraria.domain.SaleBook;
import br.com.trier.Livraria.repositories.SaleBookRepository;
import br.com.trier.Livraria.services.SaleBookService;
import br.com.trier.Livraria.services.exceptions.ObjectNotFound;

public class SaleBookServiceImpl implements SaleBookService{
	
	@Autowired
	private SaleBookRepository repository;

	@Override
	public SaleBook insert(SaleBook saleBook) {
		
		return repository.save(saleBook);
	}

	@Override
	public List<SaleBook> listAll() {
		List<SaleBook> list = repository.findAll();
		if(list.size() == 0) {
			throw new ObjectNotFound("No saleBook registered.");
		}
		
		return list;
	}

	@Override
	public SaleBook findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("SaleBook with id %s does not exist.".formatted(id)));

	}

	@Override
	public List<SaleBook> findByBookIgnoreCase(Book book) {
		List<SaleBook> list = repository.findByBookIgnoreCase(book);
		if(list.size() == 0) {
			throw new ObjectNotFound("No book found: %s.".formatted(book));
		}
		
		return list;
	}

	@Override
	public List<SaleBook> findBySaleIgnoreCase(Sale sale) {
		List<SaleBook> list = repository.findBySaleIgnoreCase(sale);
		if(list.size() == 0) {
			throw new ObjectNotFound("No sale found: %s.".formatted(sale));
		}
		
		return list;
	}

	@Override
	public SaleBook update(SaleBook saleBook) {
		findById(saleBook.getId());
		return repository.save(saleBook);
	}

	@Override
	public void delete(Integer id) {
		SaleBook saleBook = findById(id);
		repository.delete(saleBook);
		
	}

	@Override
	public List<SaleBook> findByBookAndSaleIgnoreCase(Book book, Sale sale) {
		List<SaleBook> list = repository.findByBookAndSaleIgnoreCase(book,sale);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nothing found for this search: %s.".formatted(book,sale));
		}
		
		return list;
	}

}
