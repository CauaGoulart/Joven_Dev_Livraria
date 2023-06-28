package br.com.trier.Livraria.services;

import java.util.List;
import java.util.Optional;

import br.com.trier.Livraria.domain.Client;

public interface ClientService {

	Client insert (Client client);
	List<Client> listAll();
	Client findById(Integer id);
    Client update(Client client);
    void delete(Integer id);
    Optional<Client> findByNameIgnoreCase(String name);
    Optional<Client> findByEmail(String email);
	List<Client> findByEmailAndPassword(String email,String password);
}
