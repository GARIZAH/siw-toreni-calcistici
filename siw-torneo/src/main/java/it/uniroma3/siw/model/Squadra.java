package it.uniroma3.siw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.JoinColumn;

@Entity
public class Squadra {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	 @NotBlank(message = "Il nome è obbligatorio")
	    @Size(min = 2, max = 100)
	    @Column(nullable = false)
	    private String nome;
	 
	    @NotNull(message = "L'anno di fondazione è obbligatorio")
	    @Min(value = 1800, message = "Anno di fondazione non valido")
	    @Column(nullable = false)
	    private Integer annoDiFondazione;
	 
	    @NotBlank(message = "La città è obbligatoria")
	    @Size(min = 2, max = 100)
	    @Column(nullable = false)
	    private String citta;
	    
	    @JsonIgnore 
	    @ManyToMany(mappedBy = "squadre", fetch = FetchType.LAZY)
	    private List<Torneo> torneos = new ArrayList<>();
	@OneToMany(mappedBy = "squadra", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Giocatore> giocatori=new ArrayList<>();
	@JsonIgnore 
	@OneToMany(mappedBy="squadraHome")
	private List<Partita> partiteHome=new ArrayList<>();
	@JsonIgnore 
	@OneToMany(mappedBy="squadraAway")
	private List<Partita> partiteAway=new ArrayList<>();
	
	
	public List<Torneo> getTorneos() {
		return torneos;
	}
	public void setTorneos(List<Torneo> torneos) {
		this.torneos = torneos;
	}
	public List<Giocatore> getGiocatori() {
		return giocatori;
	}
	public void setGiocatori(List<Giocatore> giocatori) {
		this.giocatori = giocatori;
	}
	public List<Partita> getPartiteHome() {
		return partiteHome;
	}
	public void setPartiteHome(List<Partita> partiteHome) {
		this.partiteHome = partiteHome;
	}
	public List<Partita> getPartiteAway() {
		return partiteAway;
	}
	public void setPartiteAway(List<Partita> partiteAway) {
		this.partiteAway = partiteAway;
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
	public Integer getAnnoDiFondazione() {
		return annoDiFondazione;
	}
	public void setAnnoDiFondazione(Integer annoDiFondazione) {
		this.annoDiFondazione = annoDiFondazione;
	}
	public String getCitta() {
		return citta;
	}
	public void setCitta(String citta) {
		this.citta = citta;
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
		Squadra other = (Squadra) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
