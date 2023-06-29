package br.com.trier.Livraria.services;

import java.util.List;
import java.util.Optional;

import br.com.trier.Livraria.domain.Phone;
import br.com.trier.Livraria.domain.Client;

public interface PhoneService {
	
	Phone insert(Phone phone);
	List<Phone> listAll();
	Phone findById(Integer id);
	Optional<Phone> findByNumber(String number);
	List<Phone> findByClient(Client client);
	List<Phone> findByClientAndNumber(Client client,String number);
	Phone update(Phone phone);
    void delete(Integer id);

}
