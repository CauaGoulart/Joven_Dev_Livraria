package br.com.trier.Livraria.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.Livraria.domain.Client;
import br.com.trier.Livraria.domain.Phone;

@Repository
public interface PhoneRepository extends JpaRepository<Phone,Integer>{
	
	Optional<Phone> findByNumber(String number);
	List<Phone> findByClient(Client client);
	List<Phone> findByClientAndNumber(Client client,String number);

}
