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

import br.com.trier.Livraria.domain.BookGenres;
import br.com.trier.Livraria.domain.dto.BookGenresDTO;
import br.com.trier.Livraria.services.GenresService;
import br.com.trier.Livraria.services.BookGenresService;
import br.com.trier.Livraria.services.BookService;

@RestController
@RequestMapping(value = "/book_genres")
public class BookGenresResource {
	
	@Autowired
	private BookGenresService service;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private GenresService genresService;
	
	@PostMapping
	public ResponseEntity<BookGenresDTO> insert(@RequestBody BookGenresDTO bookGenres) {
		BookGenres newBookGenres = service.insert(new BookGenres(bookGenres));
		return ResponseEntity.ok(newBookGenres.toDto());
	}

	@GetMapping("/{id}")
	public ResponseEntity<BookGenresDTO> findById(@PathVariable Integer id) {
		BookGenres bookGenres = service.findById(id);
        return ResponseEntity.ok(bookGenres.toDto());		
        }
    
    @PutMapping("/{id}")
	public ResponseEntity<BookGenresDTO> update(@PathVariable Integer id, @RequestBody BookGenresDTO bookGenresDTO) {
    	BookGenres bookGenres = new BookGenres(bookGenresDTO);
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
	public ResponseEntity<List<BookGenresDTO>> listAll(){
        return ResponseEntity.ok(service.listAll().stream().map((bookGenres) -> bookGenres.toDto()).toList());
    }
    
    @GetMapping("/book/{idBook}")
   	public ResponseEntity<List<BookGenresDTO>> findByBook(@PathVariable Integer idBook){
    	return ResponseEntity.ok(service.findByBook(bookService.findById(idBook)).stream().map((bookGenres) -> bookGenres.toDto()).toList());
           
    }
    
    @GetMapping("/author/{idGenres}")
   	public ResponseEntity<List<BookGenresDTO>> findByGenres(@PathVariable Integer idGenres){
    	return ResponseEntity.ok(service.findByGenres(genresService.findById(idGenres)).stream().map((bookGenres) -> bookGenres.toDto()).toList());
           
    }
    
    @GetMapping("/book-author/{idBook}/{idGenres}")
    public ResponseEntity<List<BookGenresDTO>> findByGenresAndBook(@PathVariable Integer idBook, @PathVariable Integer idGenres) {
        return ResponseEntity.ok(service.findByGenresAndBook(genresService.findById(idGenres), bookService.findById(idBook)).stream().map(BookGenres::toDto).toList());
    }

}
