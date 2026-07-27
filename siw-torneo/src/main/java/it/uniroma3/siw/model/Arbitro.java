package it.uniroma3.siw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Arbitro {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotBlank
    @Size(min = 2, max = 50)
    @Column(nullable = false)
    private String nome;
 
    @NotBlank
    @Size(min = 2, max = 50)
    @Column(nullable = false)
    private String cognome;
 
    @NotBlank
    @Column(nullable = false, unique = true)
	private String codiceArbitrale;
    @JsonIgnore 
    @OneToMany(mappedBy = "arbitro", fetch = FetchType.LAZY)
    private List<Partita> partite = new ArrayList<>();
	
	
	
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
	public String getCodiceArbitrale() {
		return codiceArbitrale;
	}
	public void setCodiceArbitrale(String codiceArbitrale) {
		this.codiceArbitrale = codiceArbitrale;
	}
	public List<Partita> getPartite() {
		return partite;
	}
	public void setPartite(List<Partita> partite) {
		this.partite = partite;
	}
	@Override
	public int hashCode() {
		return Objects.hash(codiceArbitrale);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Arbitro other = (Arbitro) obj;
		return Objects.equals(codiceArbitrale, other.codiceArbitrale);
	}
	
	
	
}
