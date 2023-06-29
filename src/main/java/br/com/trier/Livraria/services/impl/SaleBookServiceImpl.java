package br.com.trier.Livraria.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.Livraria.domain.Book;
import br.com.trier.Livraria.domain.Sale;
import br.com.trier.Livraria.domain.SaleBook;
import br.com.trier.Livraria.repositories.SaleBookRepository;
import br.com.trier.Livraria.services.SaleBookService;
import br.com.trier.Livraria.services.exceptions.ObjectNotFound;

@Service
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
			throw new ObjectNotFound("Nenhum registro encontrado.");
		}
		
		return list;
	}

	@Override
	public SaleBook findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("Nenhum resultado com o id %s.".formatted(id)));

	}

	@Override
	public List<SaleBook> findByBook(Book book) {
		List<SaleBook> list = repository.findByBook(book);
		if(list.size() == 0) {
			throw new ObjectNotFound("Livro não encontrado: %s.".formatted(book));
		}
		
		return list;
	}

	@Override
	public List<SaleBook> findBySale(Sale sale) {
		List<SaleBook> list = repository.findBySale(sale);
		if(list.size() == 0) {
			throw new ObjectNotFound("Venda não encontrada: %s.".formatted(sale));
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
	public List<SaleBook> findByBookAndSale(Book book, Sale sale) {
		List<SaleBook> list = repository.findByBookAndSale(book,sale);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhum resultado para esta pesquisa.".formatted(book,sale));
		}
		
		return list;
	}

	@Override
	public List<SaleBook> findByQt(Integer qt) {
		List<SaleBook> list = repository.findByQt(qt);
		if(list.size() == 0) {
			throw new ObjectNotFound("Quantidade não encontrada: %s.".formatted(qt));
		}
		
		return list;
	}

}
