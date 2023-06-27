package br.com.trier.Livraria.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.Livraria.domain.User;
import br.com.trier.Livraria.repositories.UserRepository;
import br.com.trier.Livraria.services.UserService;
import br.com.trier.Livraria.services.exceptions.ObjectNotFound;
import br.com.trier.Livraria.services.exceptions.IntegrityViolation;


@Service
public class UserServiceImpl implements UserService{
	@Autowired
	UserRepository repository;
	
	
	private void findByEmail(User obj) {
		Optional<User> user = repository.findByEmail(obj.getEmail());
		if(user != null && user.isEmpty()) {
			throw new IntegrityViolation("Email already registered:%s".formatted(obj.getEmail()));
		}
		
	}

	@Override
	public User insert(User user) {
		findByEmail(user);
		return repository.save(user);
	}

	@Override
	public List<User> listAll() {

		return repository.findAll();
	}

	@Override
	public User findById(Integer id) {
		Optional<User> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFound("User %s not found".formatted(id)));
	}

	@Override
	public User update(User user) {
		findByEmail(user);
		return repository.save(user);
	}


	@Override
	public void delete(Integer id) {
		User user = findById(id);
		repository.delete(user);	
	}


	@Override
	public Optional<User> findByName(String name) {
        Optional<User> lista = repository.findByName(name);
        		if(lista.isEmpty()) {
        			throw new ObjectNotFound("No user name starts with %s".formatted(name));
        		}
		return repository.findByName(name);

	}

	@Override
	public Optional<User> findByEmail(String email) {
		Optional<User> lista = repository.findByEmail(email);
		if (lista.isEmpty()) {
			throw new ObjectNotFound("No user has that email: %s".formatted(email));
		}
		return repository.findByEmail(email);

	}

	@Override
	public List<User> findByEmailAndPassword(String email, String password) {
		List<User> lista = repository.findByEmailAndPassword(email,password);
		if (lista.size() == 0) {
			throw new ObjectNotFound("No user found");
		}
		return repository.findByEmailAndPassword(email,password);

	}
}



