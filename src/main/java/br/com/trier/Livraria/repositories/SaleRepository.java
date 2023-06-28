package br.com.trier.Livraria.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.Livraria.domain.Sale;
import br.com.trier.Livraria.domain.Client;


@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer>{
	
	Optional<Sale> findByDate(LocalDateTime date);
	List<Sale> findByClient(Client client);

}
