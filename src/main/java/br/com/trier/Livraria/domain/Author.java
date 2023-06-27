package br.com.trier.Livraria.domain;

import br.com.trier.Livraria.domain.dto.AuthorDTO;
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
@Entity(name = "author")
public class Author {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_author")
	@Setter
	private Integer id;
	
	@Column(name = "name_author")
	private String name;
	
	@Column(name = "gender_author")
	private String gender;
	
	public Author(Author dto) {
		this(dto.getId(),dto.getName(),dto.getGender());
	}
	
	public AuthorDTO toDto() {
		return new AuthorDTO(id,name,gender);
	}

}
