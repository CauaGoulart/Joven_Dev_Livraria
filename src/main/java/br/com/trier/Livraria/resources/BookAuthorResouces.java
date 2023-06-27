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

import br.com.trier.Livraria.domain.BookAuthor;
import br.com.trier.Livraria.domain.dto.BookAuthorDTO;
import br.com.trier.Livraria.services.AuthorService;
import br.com.trier.Livraria.services.BookAuthorService;
import br.com.trier.Livraria.services.BookService;

@RestController
@RequestMapping(value = "/book_author")
public class BookAuthorResouces {
	@Autowired
	private BookAuthorService service;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private AuthorService authorService;
	
	@PostMapping
	public ResponseEntity<BookAuthorDTO> insert(@RequestBody BookAuthorDTO piloto) {
		BookAuthor newBookAuthor = service.insert(new BookAuthor(piloto));
		return ResponseEntity.ok(newBookAuthor.toDto());
	}

	@GetMapping("/{id}")
	public ResponseEntity<BookAuthorDTO> buscaPorCodigo(@PathVariable Integer id) {
		BookAuthor piloto = service.findById(id);
        return ResponseEntity.ok(piloto.toDto());		
        }
    
    @PutMapping("/{id}")
	public ResponseEntity<BookAuthorDTO> update(@PathVariable Integer id, @RequestBody BookAuthorDTO pilotoDTO) {
    	BookAuthor piloto = new BookAuthor(pilotoDTO);
		piloto.setId(id);
		piloto = service.update(piloto);
		return ResponseEntity.ok(piloto.toDto());
	}
    
    @DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
    	service.delete(id);
    	return ResponseEntity.ok().build();
	}
    
    @GetMapping
	public ResponseEntity<List<BookAuthorDTO>> listaTodos(){
        return ResponseEntity.ok(service.listAll().stream().map((piloto) -> piloto.toDto()).toList());
    }
    
    @GetMapping("/name/{name}")
	public ResponseEntity<List<BookAuthorDTO>> buscarPorNome(@PathVariable String name){
        return ResponseEntity.ok(service.findByName(name).stream().map((piloto) -> piloto.toDto()).toList());
	}
    
    @GetMapping("/pais/{idEquipe}")
   	public ResponseEntity<List<BookAuthorDTO>> buscarPorPais(@PathVariable Integer idEquipe){
    	return ResponseEntity.ok(service.findByPais(bookService.findById(idEquipe)).stream().map((piloto) -> piloto.toDto()).toList());
           
    }
    
    @GetMapping("/equipe/{idEquipe}")
   	public ResponseEntity<List<BookAuthorDTO>> buscarPorEquipe(@PathVariable Integer idEquipe){
    	return ResponseEntity.ok(service.findByEquipe(equipeService.findById(idEquipe)).stream().map((piloto) -> piloto.toDto()).toList());
           
    }

}
