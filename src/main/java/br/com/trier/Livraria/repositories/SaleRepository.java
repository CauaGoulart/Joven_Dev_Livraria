package br.com.trier.Livraria.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.Livraria.domain.Sale;
import br.com.trier.Livraria.domain.User;


@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer>{
	
	Optional<Sale> findByDateIgnoreCase(LocalDateTime date);
	List<Sale> findByUserIgnoreCase(User user);

}
