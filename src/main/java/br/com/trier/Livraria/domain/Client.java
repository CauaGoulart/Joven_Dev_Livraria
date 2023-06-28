package br.com.trier.Livraria.domain;

import br.com.trier.Livraria.domain.dto.ClientDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Entity(name = "client")
public class Client {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_client")
	@Setter
	private Integer id;
	
	@Column(name = "name_client")
	private String name;
	
	@Column(name = "email_client", unique = true)
	private String email;
	
	@Column(name = "password_client")
	private String password;
	
	public Client(ClientDTO dto) {
		this(dto.getId(),dto.getName(),dto.getEmail(),dto.getPassword());
	}
	
	public ClientDTO toDto() {
		return new ClientDTO(id,name,email,password);
	}

}
