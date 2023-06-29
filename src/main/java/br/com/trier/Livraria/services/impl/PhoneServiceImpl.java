package br.com.trier.Livraria.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.Livraria.domain.Client;
import br.com.trier.Livraria.domain.Phone;
import br.com.trier.Livraria.repositories.PhoneRepository;
import br.com.trier.Livraria.services.PhoneService;
import br.com.trier.Livraria.services.exceptions.ObjectNotFound;

@Service
public class PhoneServiceImpl implements PhoneService{
	
	@Autowired
	private PhoneRepository repository;

	@Override
	public Phone insert(Phone phone) {
		return repository.save(phone);
	}

	@Override
	public List<Phone> listAll() {
		List<Phone> list = repository.findAll();
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhum telefone cadastrado.");
		}
		
		return list;
	}

	@Override
	public Phone findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("Telefone com o id %s não existe".formatted(id)));

	}

	@Override
	public Optional<Phone> findByNumber(String number) {
		Optional<Phone> list = repository.findByNumber(number);
		if(list.isEmpty()) {
			throw new ObjectNotFound("Número não encontrado: %s".formatted(number));
		}
		
		return list;
	}

	@Override
	public List<Phone> findByClient(Client client) {
		List<Phone> list = repository.findByClient(client);
		if(list.size() == 0) {
			throw new ObjectNotFound("Cliente não encontrado: %s.".formatted(client));
		}
		
		return list;
	}

	@Override
	public Phone update(Phone phone) {
		findById(phone.getId());
		return repository.save(phone);
	}

	@Override
	public void delete(Integer id) {
		Phone phone = findById(id);
		repository.delete(phone);
		
	}

	@Override
	public List<Phone> findByClientAndNumber(Client client, String number) {
		List<Phone> list = repository.findByClientAndNumber(client,number);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhum resultado para esta pesquisa.");
		}
		
		return list;
	}


}
