package br.com.trier.Livraria.services;

import java.util.List;

import br.com.trier.Livraria.domain.Book;
import br.com.trier.Livraria.domain.SaleBook;
import br.com.trier.Livraria.domain.Sale;

public interface SaleBookService {
	
	SaleBook insert(SaleBook saleBook);
	List<SaleBook> listAll();
	SaleBook findById(Integer id);
	List<SaleBook> findByBook(Book book);
	List<SaleBook> findBySale(Sale sale);
	List<SaleBook> findByQt(Integer qt);
	List<SaleBook> findByBookAndSale(Book book,Sale sale);
	SaleBook update(SaleBook saleBook);
    void delete(Integer id);

}
