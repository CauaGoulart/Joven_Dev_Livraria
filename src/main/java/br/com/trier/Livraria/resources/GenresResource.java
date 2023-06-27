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

import br.com.trier.Livraria.domain.Genres;
import br.com.trier.Livraria.services.GenresService;

@RestController
@RequestMapping(value = "/book")
public class GenresResource {
	
	@Autowired
	private GenresService service;

	@PostMapping
	public ResponseEntity<Genres> insert(@RequestBody Genres livro) {
		return ResponseEntity.ok(service.insert(livro));	
		}

	@GetMapping("/{id}")
	public ResponseEntity<Genres> buscaPorCodigo(@PathVariable Integer id) {
		Genres livro = service.findById(id);
		 return ResponseEntity.ok(livro);	
	}
	
	 @GetMapping("/name/{name}")
		public ResponseEntity<List<Genres>> buscarPorNome(@PathVariable String genres){
	        return ResponseEntity.ok(service.findByGenresIgnoreCase(genres).stream().map((user) -> user).toList());
		}
	
    @GetMapping
	public ResponseEntity<List<Genres>> listaTodos(){
        return ResponseEntity.ok(service.listAll());
}
    
    @PutMapping("/{id}")
    public ResponseEntity<Genres> update(@PathVariable Integer id, @RequestBody Genres livro){
		return ResponseEntity.ok(service.update(livro));
	}
    
    @DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
    	service.delete(id);
    	return ResponseEntity.ok().build();
	}

}