package it.uniroma3.siw.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Entity
public class Partita {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
    @NotNull
    @Column(nullable = false)
    private LocalDateTime dataOra;
 
    @NotBlank
    @Size(min = 2, max = 200)
    @Column(nullable = false)
    private String luogo;
 
    @Min(value = 0)
    private Integer goalsHome;
 
    @Min(value = 0)
    private Integer goalsAway;
 
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatoPartita stato = StatoPartita.SCHEDULED;
    
    @JsonIgnore 
    @ManyToOne(fetch = FetchType.LAZY)
	private Torneo torneo;
	@ManyToOne(fetch = FetchType.LAZY)

	private Squadra squadraHome;
	 @ManyToOne(fetch = FetchType.LAZY)
	private Squadra squadraAway;
	 @ManyToOne(fetch = FetchType.LAZY)
	private Arbitro arbitro;
	@OneToMany(mappedBy = "partita", cascade = CascadeType.ALL)
	private List<Commento> commenti= new ArrayList<>();
	
	
	
	public StatoPartita getStato() {
		return stato;
	}
	public void setStato(StatoPartita stato) {
		this.stato = stato;
	}
	public List<Commento> getCommenti() {
		return commenti;
	}
	public void setCommenti(List<Commento> commenti) {
		this.commenti = commenti;
	}
	public Arbitro getArbitro() {
		return arbitro;
	}
	public void setArbitro(Arbitro arbitro) {
		this.arbitro = arbitro;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDateTime getDataOra() {
		return dataOra;
	}
	public void setDataOra(LocalDateTime dataOra) {
		this.dataOra = dataOra;
	}
	public String getLuogo() {
		return luogo;
	}
	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}
	public Integer getGoalsHome() {
		return goalsHome;
	}
	public void setGoalsHome(Integer goalsHome) {
		this.goalsHome = goalsHome;
	}
	public Integer getGoalsAway() {
		return goalsAway;
	}
	public void setGoalsAway(Integer goalsAway) {
		this.goalsAway = goalsAway;
	}
	public Torneo getTorneo() {
		return torneo;
	}
	public void setTorneo(Torneo torneo) {
		this.torneo = torneo;
	}
	public Squadra getSquadraHome() {
		return squadraHome;
	}
	public void setSquadraHome(Squadra squadraHome) {
		this.squadraHome = squadraHome;
	}
	public Squadra getSquadraAway() {
		return squadraAway;
	}
	public void setSquadraAway(Squadra squadraAway) {
		this.squadraAway = squadraAway;
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
		Partita other = (Partita) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
}
