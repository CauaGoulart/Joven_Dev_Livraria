package br.com.trier.Livraria.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.Livraria.domain.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>{
	
	Optional<Client> findByNameIgnoreCase(String name);
	Optional<Client> findByEmail(String email);
	List<Client> findByEmailAndPassword(String email,String password);

}
