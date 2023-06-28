package br.com.trier.Livraria.domain;

import br.com.trier.Livraria.domain.dto.BookAuthorDTO;
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
@Entity(name = "book_author")
public class BookAuthor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_book-author")
	@Setter
	private Integer id;
	
	@ManyToOne
	private Book book;
	
	@ManyToOne
	private Author author;
	
	public BookAuthor(BookAuthorDTO dto) {
		this(dto.getId(),dto.getBook(),dto.getAuthor());
	}
	
	public BookAuthorDTO toDto() {
		return new BookAuthorDTO(id,book,author);
	}

}
