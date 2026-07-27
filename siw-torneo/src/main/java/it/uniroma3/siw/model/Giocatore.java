package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
@Entity
public class Giocatore {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String nome;
	private String cognome;
	private LocalDate dataDiNascita;
	@Enumerated(EnumType.STRING)
	private GiocatoreRuolo ruolo;
	

	private Double altezza;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnore
	private Squadra squadra;

	
	public GiocatoreRuolo getRuolo() {
		return ruolo;
	}

	public void setRuolo(GiocatoreRuolo ruolo) {
		this.ruolo = ruolo;
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

	public LocalDate getDataDiNascita() {
		return dataDiNascita;
	}

	public void setDataDiNascita(LocalDate dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}

	public Double getAltezza() {
		return altezza;
	}

	public void setAltezza(Double altezza) {
		this.altezza = altezza;
	}

	public Squadra getSquadra() {
		return squadra;
	}

	public void setSquadra(Squadra squadra) {
		this.squadra = squadra;
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
		Giocatore other = (Giocatore) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
}
