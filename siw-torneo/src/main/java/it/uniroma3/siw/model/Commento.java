package it.uniroma3.siw.model;


import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Commento {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	 @NotBlank
	    @Size(min = 1, max = 500)
	    @Column(nullable = false, length = 500)
	    private String testo;
	 
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Utente utente;
	@ManyToOne(fetch = FetchType.LAZY)
	private Partita partita;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTesto() {
		return testo;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}
	
	public Utente getUtente() {
		return utente;
	}
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	public Partita getPartita() {
		return partita;
	}
	public void setPartita(Partita partita) {
		this.partita = partita;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Commento other = (Commento) obj;
		return Objects.equals(id, other.id);
	}
	
 
}
