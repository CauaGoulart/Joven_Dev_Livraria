package br.com.trier.Livraria.config.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.trier.Livraria.domain.Client;
import br.com.trier.Livraria.repositories.ClientRepository;

@Component
public class JwtUserDetailService implements UserDetailsService{
	
	@Autowired
	private ClientRepository repository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Client client = repository.findByEmail(email).orElseThrow(null);
		return User.builder()
				.username(client.getEmail())
				.password(encoder.encode(client.getPassword()))
				.roles(client.getRoles().split(","))
				.build();
	}
}
