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

import br.com.trier.Livraria.domain.Genres;
import br.com.trier.Livraria.services.GenresService;

@RestController
@RequestMapping(value = "/genres")
public class GenresResource {
	
	@Autowired
	private GenresService service;

	@Secured({"ROLE_ADMIN"})
	@PostMapping
	public ResponseEntity<Genres> insert(@RequestBody Genres genres) {
		return ResponseEntity.ok(service.insert(genres));	
		}

	@Secured({"ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<Genres> findById(@PathVariable Integer id) {
		Genres genres = service.findById(id);
		 return ResponseEntity.ok(genres);	
	}
	
	 @Secured({"ROLE_USER"})
	 @GetMapping("/name/{name}")
		public ResponseEntity<List<Genres>> findByGenresIgnoreCase(@PathVariable String genres){
	        return ResponseEntity.ok(service.findByGenresIgnoreCase(genres).stream().map((user) -> user).toList());
		}
	
	@Secured({"ROLE_USER"})
    @GetMapping
	public ResponseEntity<List<Genres>> listAll(){
        return ResponseEntity.ok(service.listAll());
}
    
    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<Genres> update(@PathVariable Integer id, @RequestBody Genres genres){
		return ResponseEntity.ok(service.update(genres));
	}
    
    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
    	service.delete(id);
    	return ResponseEntity.ok().build();
	}

}