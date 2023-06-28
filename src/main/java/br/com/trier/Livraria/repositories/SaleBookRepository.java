package br.com.trier.Livraria.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.Livraria.domain.Book;
import br.com.trier.Livraria.domain.Sale;
import br.com.trier.Livraria.domain.SaleBook;

@Repository
public interface SaleBookRepository extends JpaRepository<SaleBook, Integer>{
	
	List<SaleBook> findByBook(Book book);
	List<SaleBook> findBySale(Sale sale);
	List<SaleBook> findByBookAndSale(Book book,Sale sale);

}
