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

import br.com.trier.Livraria.domain.Author;
import br.com.trier.Livraria.domain.dto.AuthorDTO;
import br.com.trier.Livraria.services.AuthorService;

@RestController
@RequestMapping(value = "/author")
public class AuthorResource {
	
	@Autowired
	private AuthorService service;

	@Secured({"ROLE_ADMIN"})
	@PostMapping
	public ResponseEntity<Author> insert(@RequestBody Author author) {
		return ResponseEntity.ok(service.insert(author));	
		}

	@Secured({"ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<AuthorDTO> findById(@PathVariable Integer id) {
		Author author = service.findById(id);
		 return ResponseEntity.ok(author.toDto());	
	}
	
	@Secured({"ROLE_USER"})
	 @GetMapping("/name/{name}")
		public ResponseEntity<List<Author>> findByName(@PathVariable String name){
	        return ResponseEntity.ok(service.findByNameIgnoreCase(name).stream().map((author) -> author).toList());
		}
	 
	 @Secured({"ROLE_USER"})
	 @GetMapping("/gender/{gender}")
		public ResponseEntity<List<Author>> findByGender(@PathVariable String gender){
	        return ResponseEntity.ok(service.findByGenderIgnoreCase(gender).stream().map((author) -> author).toList());
		}
	
	@Secured({"ROLE_USER"})
    @GetMapping
	public ResponseEntity<List<AuthorDTO>> listAll(){
        return ResponseEntity.ok(service.listAll().stream().map((author) -> author.toDto()).toList());
}
    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<Author> update(@PathVariable Integer id, @RequestBody Author author){
		return ResponseEntity.ok(service.update(author));
	}
    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
    	service.delete(id);
    	return ResponseEntity.ok().build();
	}

}
