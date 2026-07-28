package it.uniroma3.siw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import it.uniroma3.siw.validation.NotFutureYear;
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
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.JoinColumn;

@Entity
public class Torneo {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotBlank
    @Size(min = 2, max = 100)
    @Column(nullable = false)
    private String nome;
 
    @NotNull
    @Min(value = 1900)
	@Max(value = 2030)
    @Column(nullable = false)
    private Integer anno;
 
    @Size(max = 500)
    @Column(length = 500)
    private String descrizione;
 
    // Relazione: una squadra partecipa a uno o più tornei
    //JoinTable gestita dal lato Torneo (proprietario della relazione)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "squadra_torneo",
        joinColumns = @JoinColumn(name = "torneo_id"),
        inverseJoinColumns = @JoinColumn(name = "squadra_id")
    )
    private List<Squadra> squadre = new ArrayList<>();
 
    
    @OneToMany(mappedBy = "torneo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Partita> partite = new ArrayList<>();
	
	
	public List<Squadra> getSquadre() {
		return squadre;
	}
	public void setSquadre(List<Squadra> squadre) {
		this.squadre = squadre;
	}
	public List<Partita> getPartite() {
		return partite;
	}
	public void setPartite(List<Partita> partite) {
		this.partite = partite;
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
	public Integer getAnno() {
		return anno;
	}
	public void setAnno(Integer anno) {
		this.anno = anno;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
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
		Torneo other = (Torneo) obj;
		return Objects.equals(id, other.id);
	}

	
}
