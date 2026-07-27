package it.uniroma3.siw.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Utente {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@NotBlank
	private String nome;
	@NotBlank
	private String cognome;
	@Column(nullable = false, unique = true, length = 100)
	private String email;	
	@OneToOne(mappedBy = "utente") 
	private Credentials credentials;
	
	

	
	public Credentials getCredentials() {
		return credentials;
	}
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public int hashCode() {
		return Objects.hash(email);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Utente other = (Utente) obj;
		return Objects.equals(email, other.email);
	}
	
	
	
}
