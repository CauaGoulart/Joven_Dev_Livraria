package br.com.trier.Livraria.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import br.com.trier.Livraria.domain.Sale;
import br.com.trier.Livraria.domain.Client;

public interface SaleService {
	
	Sale insert(Sale sale);
	List<Sale> listAll();
	Sale findById(Integer id);
	Optional<Sale> findByDate(LocalDateTime date);
	List<Sale> findByClient(Client client);
	Sale update(Sale sale);
    void delete(Integer id);

}
