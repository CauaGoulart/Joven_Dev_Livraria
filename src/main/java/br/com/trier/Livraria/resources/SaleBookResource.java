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

import br.com.trier.Livraria.domain.SaleBook;
import br.com.trier.Livraria.domain.dto.SaleBookDTO;
import br.com.trier.Livraria.services.SaleBookService;
import br.com.trier.Livraria.services.SaleService;
import br.com.trier.Livraria.services.BookService;

@RestController
@RequestMapping(value = "/sale_book")
public class SaleBookResource {
	
	@Autowired
	private SaleBookService service;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private SaleService saleService;
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping
	public ResponseEntity<SaleBookDTO> insert(@RequestBody SaleBookDTO saleBook) {
		SaleBook newSaleBook = service.insert(new SaleBook(saleBook));
		return ResponseEntity.ok(newSaleBook.toDto());
	}

	@Secured({"ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<SaleBookDTO> findById(@PathVariable Integer id) {
		SaleBook saleBook = service.findById(id);
        return ResponseEntity.ok(saleBook.toDto());		
        }
    
	@Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
	public ResponseEntity<SaleBookDTO> update(@PathVariable Integer id, @RequestBody SaleBookDTO saleBookDTO) {
    	SaleBook saleBook = new SaleBook(saleBookDTO);
		saleBook.setId(id);
		saleBook = service.update(saleBook);
		return ResponseEntity.ok(saleBook.toDto());
	}
    
	@Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
    	service.delete(id);
    	return ResponseEntity.ok().build();
	}
    
	@Secured({"ROLE_USER"})
    @GetMapping
	public ResponseEntity<List<SaleBookDTO>> listAll(){
        return ResponseEntity.ok(service.listAll().stream().map((saleBook) -> saleBook.toDto()).toList());
    }
    
	@Secured({"ROLE_USER"})
    @GetMapping("/book/{idBook}")
   	public ResponseEntity<List<SaleBookDTO>> findByBook(@PathVariable Integer idBook){
    	return ResponseEntity.ok(service.findByBook(bookService.findById(idBook)).stream().map((saleBook) -> saleBook.toDto()).toList());
           
    }
    
	@Secured({"ROLE_USER"})
    @GetMapping("/author/{idSale}")
   	public ResponseEntity<List<SaleBookDTO>> findBySale(@PathVariable Integer idSale){
    	return ResponseEntity.ok(service.findBySale(saleService.findById(idSale)).stream().map((saleBook) -> saleBook.toDto()).toList());
           
    }
    
	@Secured({"ROLE_USER"})
    @GetMapping("/author/{qt}")
   	public ResponseEntity<List<SaleBookDTO>> findByQt(@PathVariable Integer qt){
    	return ResponseEntity.ok(service.findByQt(qt).stream().map((saleBook) -> saleBook.toDto()).toList());
           
    }
    
	@Secured({"ROLE_USER"})
    @GetMapping("/book-author/{idBook}/{idSale}")
    public ResponseEntity<List<SaleBookDTO>> findByBookAndSale(@PathVariable Integer idBook, @PathVariable Integer idSale) {
        return ResponseEntity.ok(service.findByBookAndSale(bookService.findById(idBook), saleService.findById(idSale)).stream().map(SaleBook::toDto).toList());
    }

}
