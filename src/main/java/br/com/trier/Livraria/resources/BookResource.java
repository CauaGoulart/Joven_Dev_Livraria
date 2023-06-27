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

import br.com.trier.Livraria.domain.Book;
import br.com.trier.Livraria.domain.dto.BookDTO;
import br.com.trier.Livraria.services.BookService;

@RestController
@RequestMapping(value = "/book")
public class BookResource {
	
	@Autowired
	private BookService service;

	@PostMapping
	public ResponseEntity<Book> insert(@RequestBody Book book) {
		return ResponseEntity.ok(service.insert(book));	
		}

	@GetMapping("/{id}")
	public ResponseEntity<BookDTO> findById(@PathVariable Integer id) {
		Book book = service.findById(id);
		 return ResponseEntity.ok(book.toDto());	
	}
	
	 @GetMapping("/name/{name}")
		public ResponseEntity<List<Book>> findByTitleIgnoreCase(@PathVariable String title){
	        return ResponseEntity.ok(service.findByTitleIgnoreCase(title).stream().map((book) -> book).toList());
		}
	 
	 @GetMapping("/price/{price}")
		public ResponseEntity<List<Book>> findByPrice(@PathVariable Integer price){
	        return ResponseEntity.ok(service.findByPrice(price).stream().map((book) -> book).toList());
		}
	
    @GetMapping
	public ResponseEntity<List<BookDTO>> listAll(){
        return ResponseEntity.ok(service.listAll().stream().map((book) -> book.toDto()).toList());
}
    
    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable Integer id, @RequestBody Book livro){
		return ResponseEntity.ok(service.update(livro));
	}
    
    @DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
    	service.delete(id);
    	return ResponseEntity.ok().build();
	}

}
