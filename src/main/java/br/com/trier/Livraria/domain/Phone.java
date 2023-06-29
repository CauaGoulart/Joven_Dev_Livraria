package br.com.trier.Livraria.domain;

import br.com.trier.Livraria.domain.dto.PhoneDTO;
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
@Entity(name = "phone")
public class Phone {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_phone")
	@Setter
	private Integer id;
	
	@Column(name = "phone_number")
	private String number;
	
	@ManyToOne
	private Client client;
	
	public Phone(PhoneDTO dto) {
		this(dto.getId(),dto.getNumber(),dto.getClient());
	}
	
	public PhoneDTO toDto() {
		return new PhoneDTO(id,number,client);
	}

}
