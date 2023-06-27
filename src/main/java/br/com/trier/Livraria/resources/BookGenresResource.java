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
@RequestMapping(value = "/book_genres")
public class BookGenresResource {
	
	@Autowired
	private BookAuthorService service;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private AuthorService authorService;
	
	@PostMapping
	public ResponseEntity<BookAuthorDTO> insert(@RequestBody BookAuthorDTO bookGenres) {
		BookAuthor newBookAuthor = service.insert(new BookAuthor(bookGenres));
		return ResponseEntity.ok(newBookAuthor.toDto());
	}

	@GetMapping("/{id}")
	public ResponseEntity<BookAuthorDTO> findById(@PathVariable Integer id) {
		BookAuthor bookGenres = service.findById(id);
        return ResponseEntity.ok(bookGenres.toDto());		
        }
    
    @PutMapping("/{id}")
	public ResponseEntity<BookAuthorDTO> update(@PathVariable Integer id, @RequestBody BookAuthorDTO bookGenresDTO) {
    	BookAuthor bookGenres = new BookAuthor(bookGenresDTO);
		bookGenres.setId(id);
		bookGenres = service.update(bookGenres);
		return ResponseEntity.ok(bookGenres.toDto());
	}
    
    @DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
    	service.delete(id);
    	return ResponseEntity.ok().build();
	}
    
    @GetMapping
	public ResponseEntity<List<BookAuthorDTO>> listAll(){
        return ResponseEntity.ok(service.listAll().stream().map((bookGenres) -> bookGenres.toDto()).toList());
    }
    
    @GetMapping("/book/{idBook}")
   	public ResponseEntity<List<BookAuthorDTO>> findByBookIgnoreCase(@PathVariable Integer idBook){
    	return ResponseEntity.ok(service.findByBookIgnoreCase(bookService.findById(idBook)).stream().map((bookGenres) -> bookGenres.toDto()).toList());
           
    }
    
    @GetMapping("/author/{idGenres}")
   	public ResponseEntity<List<BookAuthorDTO>> findByAuthorIgnoreCase(@PathVariable Integer idGenres){
    	return ResponseEntity.ok(service.findByAuthorIgnoreCase(authorService.findById(idGenres)).stream().map((bookGenres) -> bookGenres.toDto()).toList());
           
    }
    
    @GetMapping("/book-author/{idBook}/{idGenres}")
    public ResponseEntity<List<BookAuthorDTO>> findByBookAndAuthorIgnoreCase(@PathVariable Integer idBook, @PathVariable Integer idGenres) {
        return ResponseEntity.ok(service.findByBookAndAuthorIgnoreCase(bookService.findById(idBook), authorService.findById(idGenres)).stream().map(BookAuthor::toDto).toList());
    }

}
