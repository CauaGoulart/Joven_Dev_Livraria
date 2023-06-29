package br.com.trier.Livraria.domain;

import java.time.LocalDateTime;

import br.com.trier.Livraria.domain.dto.SaleDTO;
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
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "sale")
public class Sale {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_sale")
	@Setter
	private Integer id;
	
	@Column(name = "date_sale")
	private LocalDateTime date;
	
	@ManyToOne
	private Client client;

	public Sale(SaleDTO dto) {
		this(dto.getId(),dto.getDate(),dto.getClient());
	}
	
	public SaleDTO toDto() {
		return new SaleDTO(id, date, client);
	}
}

