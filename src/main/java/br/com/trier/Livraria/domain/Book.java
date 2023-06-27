package br.com.trier.Livraria.domain;

import br.com.trier.Livraria.domain.dto.BookDTO;
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
@Entity(name = "book")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_livro")
	@Setter
	private Integer id;
	
	@Column(name = "title_book")
	private String title;
	
	@Column(name = "price_book")
	private Integer price;
	
	public Book(BookDTO dto) {
		this(dto.getId(),dto.getTitle(),dto.getPrice());
	}
	
	public BookDTO toDto() {
		return new BookDTO(id,title,price);
	}

}
