package br.com.trier.Livraria.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.Livraria.domain.Client;
import br.com.trier.Livraria.domain.dto.ClientDTO;
import br.com.trier.Livraria.services.ClientService;

@RestController
@RequestMapping(value = "/client")
public class ClientResources {
	
	@Autowired
	private ClientService service;
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping
	public ResponseEntity<ClientDTO> insert(@RequestBody ClientDTO client) {
		Client newUser = service.insert(new Client(client));
		return ResponseEntity.ok(newUser.toDto());
	}
	
	@Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
	public ResponseEntity<ClientDTO> update(@PathVariable Integer id, @RequestBody ClientDTO clientDTO) {
    	Client client = new Client(clientDTO);
		client.setId(id);
		client = service.update(client);
		return ResponseEntity.ok(client.toDto());
	}
	
	@Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
    	service.delete(id);
    	return ResponseEntity.ok().build();
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<ClientDTO> findById(@PathVariable Integer id) {
		Client client = service.findById(id);
        return ResponseEntity.ok(client.toDto());	
        }
	
	@Secured({"ROLE_USER"})
    @GetMapping
	public ResponseEntity<List<ClientDTO>> listAll(){
    return ResponseEntity.ok(service.listAll().stream().map((client) -> client.toDto()).toList());
    }  
	
	@Secured({"ROLE_USER"})
    @GetMapping("/name/{name}")
	public ResponseEntity<List<ClientDTO>> findByNameIgnoreCase(@PathVariable String name){
        return ResponseEntity.ok(service.findByNameIgnoreCase(name).stream().map((client) -> client.toDto()).toList());
	}
    
	@Secured({"ROLE_USER"})
    @GetMapping("/email/{email}")
	public ResponseEntity<List<ClientDTO>> findByEmail(@PathVariable String email){
        return ResponseEntity.ok(service.findByEmail(email).stream().map((client) -> client.toDto()).toList());
	}

}
