package br.com.trier.Livraria.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.Livraria.domain.User;
import br.com.trier.Livraria.domain.dto.UserDTO;
import br.com.trier.Livraria.services.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserResources {
	
	@Autowired
	private UserService service;
	
	@PostMapping
	public ResponseEntity<UserDTO> insert(@RequestBody UserDTO user) {
		User newUser = service.insert(new User(user));
		return ResponseEntity.ok(newUser.toDto());
	}
	
    @PutMapping("/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
    	User user = new User(userDTO);
		user.setId(id);
		user = service.update(user);
		return ResponseEntity.ok(user.toDto());
	}
	
    @DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
    	service.delete(id);
    	return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Integer id) {
		User user = service.findById(id);
        return ResponseEntity.ok(user.toDto());	
        }
	
    @GetMapping
	public ResponseEntity<List<UserDTO>> listAll(){
    return ResponseEntity.ok(service.listAll().stream().map((user) -> user.toDto()).toList());
    }  
	
    @GetMapping("/name/{name}")
	public ResponseEntity<List<UserDTO>> findByName(@PathVariable String name){
        return ResponseEntity.ok(service.findByName(name).stream().map((user) -> user.toDto()).toList());
	}
    
    @GetMapping("/email/{email}")
	public ResponseEntity<List<UserDTO>> findByEmail(@PathVariable String email){
        return ResponseEntity.ok(service.findByEmail(email).stream().map((user) -> user.toDto()).toList());
	}

}
