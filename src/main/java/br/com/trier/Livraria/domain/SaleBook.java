package br.com.trier.Livraria.domain;


import br.com.trier.Livraria.domain.dto.SaleBookDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "sale_book")
public class SaleBook {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_sale")
	@Setter
	private Integer id;
	
	@ManyToOne
	private Sale sale;
	
	@ManyToOne
	private Book book;

	public SaleBook(SaleBookDTO dto) {
		this(dto.getId(),dto.getSale(),dto.getBook());
	}
	
	public SaleBookDTO toDto() {
		return new SaleBookDTO(id,sale,book);
	}
	
}
