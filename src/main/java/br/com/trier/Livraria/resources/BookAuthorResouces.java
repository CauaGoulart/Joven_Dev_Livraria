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
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping
	public ResponseEntity<BookAuthorDTO> insert(@RequestBody BookAuthorDTO authorBook) {
		BookAuthor newBookAuthor = service.insert(new BookAuthor(authorBook));
		return ResponseEntity.ok(newBookAuthor.toDto());
	}

	@Secured({"ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<BookAuthorDTO> findById(@PathVariable Integer id) {
		BookAuthor authorBook = service.findById(id);
        return ResponseEntity.ok(authorBook.toDto());		
        }
    
	@Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
	public ResponseEntity<BookAuthorDTO> update(@PathVariable Integer id, @RequestBody BookAuthorDTO authorBookDTO) {
    	BookAuthor authorBook = new BookAuthor(authorBookDTO);
		authorBook.setId(id);
		authorBook = service.update(authorBook);
		return ResponseEntity.ok(authorBook.toDto());
	}
    
	@Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
    	service.delete(id);
    	return ResponseEntity.ok().build();
	}
    
	@Secured({"ROLE_USER"})
    @GetMapping
	public ResponseEntity<List<BookAuthorDTO>> listAll(){
        return ResponseEntity.ok(service.listAll().stream().map((authorBook) -> authorBook.toDto()).toList());
    }
    
	@Secured({"ROLE_USER"})
    @GetMapping("/book/{idBook}")
   	public ResponseEntity<List<BookAuthorDTO>> findByBook(@PathVariable Integer idBook){
    	return ResponseEntity.ok(service.findByBook(bookService.findById(idBook)).stream().map((authorBook) -> authorBook.toDto()).toList());
           
    }
    
	@Secured({"ROLE_USER"})
    @GetMapping("/author/{idAuthor}")
   	public ResponseEntity<List<BookAuthorDTO>> findByAuthor(@PathVariable Integer idAuthor){
    	return ResponseEntity.ok(service.findByAuthor(authorService.findById(idAuthor)).stream().map((authorBook) -> authorBook.toDto()).toList());
           
    }
    
	@Secured({"ROLE_USER"})
    @GetMapping("/book-author/{idBook}/{idAuthor}")
    public ResponseEntity<List<BookAuthorDTO>> findByBookAndAuthor(@PathVariable Integer idBook, @PathVariable Integer idAuthor) {
        return ResponseEntity.ok(service.findByBookAndAuthor(bookService.findById(idBook), authorService.findById(idAuthor)).stream().map(BookAuthor::toDto).toList());
    }

}
