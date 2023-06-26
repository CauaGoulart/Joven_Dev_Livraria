package br.com.trier.Livraria.domain;

import br.com.trier.springmatutino.domain.User;
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
@Entity(name = "usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	@Setter
	private Integer id;
	
	@Column(name = "nome_usuario")
	private String name;
	
	@Column(name = "email_usuario", unique = true)
	private String email;
	
	@Column(name = "senha_usuario")
	private String password;

}
