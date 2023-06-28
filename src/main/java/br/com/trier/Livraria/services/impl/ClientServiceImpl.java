package br.com.trier.Livraria.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.Livraria.domain.Client;
import br.com.trier.Livraria.repositories.ClientRepository;
import br.com.trier.Livraria.services.ClientService;
import br.com.trier.Livraria.services.exceptions.ObjectNotFound;
import br.com.trier.Livraria.services.exceptions.IntegrityViolation;


@Service
public class ClientServiceImpl implements ClientService{
	@Autowired
	ClientRepository repository;
	
	
	private void validateClient(Client obj) {
	    validateName(obj.getName());
	    validateEmail(obj.getEmail());
	    validatePassword(obj.getPassword());
	}

	private void validateName(String name) {
	    if (name.isBlank()) {
	        throw new IntegrityViolation("Nome não pode estar vazio.");
	    }
	}

	private void validateEmail(String email) {
	    if (email.isBlank()) {
	        throw new IntegrityViolation("Email não pode ser vazio.");
	    }
	    Optional<Client> existingClient = repository.findByEmail(email);
	    if (existingClient.isPresent()) {
	        throw new IntegrityViolation("Email ja cadastrado: %s".formatted(email));
	    }
	}

	private void validatePassword(String password) {
	    if (password.isBlank()) {
	        throw new IntegrityViolation("Senha não pode estar vazia.");
	    }
	}


	@Override
	public Client insert(Client client) {
		validateClient(client);
		return repository.save(client);
	}

	@Override
	public List<Client> listAll() {

		return repository.findAll();
	}

	@Override
	public Client findById(Integer id) {
		Optional<Client> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFound("Cliente %s não encontrado".formatted(id)));
	}

	@Override
	public Client update(Client client) {
		validateClient(client);
		return repository.save(client);
	}


	@Override
	public void delete(Integer id) {
		Client client = findById(id);
		repository.delete(client);	
	}


	@Override
	public Optional<Client> findByNameIgnoreCase(String name) {
        Optional<Client> lista = repository.findByNameIgnoreCase(name);
        		if(lista.isEmpty()) {
        			throw new ObjectNotFound("Nenhum cliente que possui o nome %s".formatted(name));
        		}
		return repository.findByNameIgnoreCase(name);

	}

	@Override
	public Optional<Client> findByEmail(String email) {
	    Optional<Client> client = repository.findByEmail(email);
	    if (client.isEmpty()) {
	        throw new ObjectNotFound("Nenhum cliente com esse email: %s".formatted(email));
	    }
	    return client;
	}


	@Override
	public List<Client> findByEmailAndPassword(String email, String password) {
		List<Client> lista = repository.findByEmailAndPassword(email,password);
		if (lista.size() == 0) {
			throw new ObjectNotFound("Nenhum cliente encontrado.");
		}
		return repository.findByEmailAndPassword(email,password);

	}
}



