package br.com.trier.Livraria.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.Livraria.domain.Book;
import br.com.trier.Livraria.domain.Sale;
import br.com.trier.Livraria.domain.SaleBook;

@Repository
public interface SaleBookRepository extends JpaRepository<SaleBook, Integer>{
	
	List<SaleBook> findByBookIgnoreCase(Book book);
	List<SaleBook> findBySaleIgnoreCase(Sale sale);
	List<SaleBook> findByBookAndSaleIgnoreCase(Book book,Sale sale);

}
