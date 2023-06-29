package br.com.trier.Livraria.resources;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

import br.com.trier.Livraria.Utils.DateUtils;
import br.com.trier.Livraria.domain.Sale;
import br.com.trier.Livraria.domain.dto.SaleDTO;
import br.com.trier.Livraria.services.SaleService;
import br.com.trier.Livraria.services.ClientService;

@RestController
@RequestMapping(value = "/sale")
public class SaleResource {
	
	@Autowired
	private SaleService service;
	
	@Autowired
	private ClientService clientService;
	
	@PostMapping
	public ResponseEntity<SaleDTO> insert(@RequestBody SaleDTO sale) {
		Sale newSale = service.insert(new Sale(sale));
		return ResponseEntity.ok(newSale.toDto());
	}

	@GetMapping("/{id}")
	public ResponseEntity<SaleDTO> findById(@PathVariable Integer id) {
		Sale sale = service.findById(id);
        return ResponseEntity.ok(sale.toDto());		
        }
    
    @PutMapping("/{id}")
	public ResponseEntity<SaleDTO> update(@PathVariable Integer id, @RequestBody SaleDTO saleDTO) {
    	Sale sale = new Sale(saleDTO);
		sale.setId(id);
		sale = service.update(sale);
		return ResponseEntity.ok(sale.toDto());
	}
    
    @DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
    	service.delete(id);
    	return ResponseEntity.ok().build();
	}
    
    @GetMapping
	public ResponseEntity<List<SaleDTO>> listAll(){
        return ResponseEntity.ok(service.listAll().stream().map((sale) -> sale.toDto()).toList());
    }
    
    @GetMapping("/user/{idClient}")
   	public ResponseEntity<List<SaleDTO>> findByClient(@PathVariable Integer idClient){
    	return ResponseEntity.ok(service.findByClient(clientService.findById(idClient)).stream().map((sale) -> sale.toDto()).toList());
           
    }
    
    @GetMapping("/date/{date}")
    public ResponseEntity<List<SaleDTO>> findByDate(@PathVariable("date") String dateStr) {
        LocalDateTime date = DateUtils.strToLocalDateTime(dateStr);
        Optional<Sale> sales = service.findByDate(date);
        return ResponseEntity.ok(sales.stream().map(Sale::toDto).toList());
    }

}
