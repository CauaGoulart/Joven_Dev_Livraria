package br.com.trier.Livraria.services;

import java.util.List;

import br.com.trier.Livraria.domain.Book;
import br.com.trier.Livraria.domain.SaleBook;
import br.com.trier.Livraria.domain.Sale;

public interface SaleBookService {
	
	SaleBook insert(SaleBook saleBook);
	List<SaleBook> listAll();
	SaleBook findById(Integer id);
	List<SaleBook> findByBookIgnoreCase(Book book);
	List<SaleBook> findBySaleIgnoreCase(Sale sale);
	List<SaleBook> findByBookAndSaleIgnoreCase(Book book,Sale sale);
	SaleBook update(SaleBook saleBook);
    void delete(Integer id);

}
