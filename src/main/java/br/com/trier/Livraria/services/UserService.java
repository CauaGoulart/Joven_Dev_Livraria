package br.com.trier.Livraria.services;

import java.util.List;
import java.util.Optional;

import br.com.trier.Livraria.domain.User;

public interface UserService {

	User insert (User user);
	List<User> listAll();
	User findById(Integer id);
    User update(User user);
    void delete(Integer id);
    Optional<User> findByName(String name);
    Optional<User> findByEmail(String email);
	List<User> findByEmailAndPassword(String email,String password);
}
