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

import br.com.trier.Livraria.domain.Phone;
import br.com.trier.Livraria.domain.dto.PhoneDTO;
import br.com.trier.Livraria.services.PhoneService;
import br.com.trier.Livraria.services.ClientService;

@RestController
@RequestMapping(value = "/phone")
public class PhoneResource {
	
	@Autowired
	private PhoneService service;
	
	@Autowired
	private ClientService clientService;
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping
	public ResponseEntity<PhoneDTO> insert(@RequestBody PhoneDTO phone) {
		Phone newPhone = service.insert(new Phone(phone));
		return ResponseEntity.ok(newPhone.toDto());
	}

	@Secured({"ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<PhoneDTO> findById(@PathVariable Integer id) {
		Phone phone = service.findById(id);
        return ResponseEntity.ok(phone.toDto());		
        }
    
	@Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
	public ResponseEntity<PhoneDTO> update(@PathVariable Integer id, @RequestBody PhoneDTO phoneDTO) {
    	Phone phone = new Phone(phoneDTO);
		phone.setId(id);
		phone = service.update(phone);
		return ResponseEntity.ok(phone.toDto());
	}
    
	@Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
    	service.delete(id);
    	return ResponseEntity.ok().build();
	}
    
	@Secured({"ROLE_USER"})
    @GetMapping
	public ResponseEntity<List<PhoneDTO>> listAll(){
        return ResponseEntity.ok(service.listAll().stream().map((phone) -> phone.toDto()).toList());
    }
    
	@Secured({"ROLE_USER"})
    @GetMapping("/client/{idClient}")
   	public ResponseEntity<List<PhoneDTO>> findByClient(@PathVariable Integer idClient){
    	return ResponseEntity.ok(service.findByClient(clientService.findById(idClient)).stream().map((phone) -> phone.toDto()).toList());
           
    }
    
	@Secured({"ROLE_USER"})
    @GetMapping("/author/{number}")
   	public ResponseEntity<List<PhoneDTO>> findByNumber(@PathVariable String number){
    	return ResponseEntity.ok(service.findByNumber(number).stream().map((phone) -> phone.toDto()).toList());
           
    }
    
	@Secured({"ROLE_USER"})
    @GetMapping("/client-author/{idClient}/{number}")
    public ResponseEntity<List<PhoneDTO>> findByClientAndNumber(@PathVariable Integer idClient, @PathVariable String number) {
        return ResponseEntity.ok(service.findByClientAndNumber(clientService.findById(idClient), number).stream().map(Phone::toDto).toList());
    }

}
