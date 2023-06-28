package br.com.trier.Livraria.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.Livraria.domain.Sale;
import br.com.trier.Livraria.domain.Client;
import br.com.trier.Livraria.repositories.SaleRepository;
import br.com.trier.Livraria.services.SaleService;
import br.com.trier.Livraria.services.exceptions.ObjectNotFound;

@Service
public class SaleServiceImpl implements SaleService{
	
	@Autowired
	private SaleRepository repository;

	@Override
	public Sale insert(Sale sale) {
		return repository.save(sale);
	}

	@Override
	public List<Sale> listAll() {
		List<Sale> list = repository.findAll();
		if(list.size() == 0) {
			throw new ObjectNotFound("No sale registered.");
		}
		
		return list;
	}

	@Override
	public Sale findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("Sale with id %s does not exist.".formatted(id)));

	}

	@Override
	public Sale update(Sale sale) {
		findById(sale.getId());
		return repository.save(sale);
	}

	@Override
	public void delete(Integer id) {
		Sale sale = findById(id);
		repository.delete(sale);
		
	}

	@Override
	public Optional<Sale> findByDate(LocalDateTime date) {
		Optional<Sale> lista = repository.findByDate(date);
		if (lista.isEmpty()) {
			throw new ObjectNotFound("No sale made in this date: %s." .formatted(date));
		}
		return repository.findByDate(date);

	}

	@Override
	public List<Sale> findByClient(Client client) {
		List<Sale> list = repository.findByClient(client);
		if(list.size() == 0) {
			throw new ObjectNotFound("No client found: %s.".formatted(client));
		}
		
		return list;
	}

}
