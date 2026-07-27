package it.uniroma3.siw.dto;

import it.uniroma3.siw.model.Squadra;

public class Classifica {
	private Squadra squadra;
    private int punti = 0;
    private int giocate = 0;
    private int vittorie = 0;
    private int pareggi = 0;
    private int sconfitte = 0;

    public Classifica(Squadra squadra) {
        this.squadra = squadra;
    }

    // Metodo per aggiornare le statistiche in base ai gol fatti/subiti
    public void aggiungiRisultato(int golFatti, int golSubiti) {
        this.giocate++;
        if (golFatti > golSubiti) {
            this.punti += 3;
            this.vittorie++;
        } else if (golFatti == golSubiti) {
            this.punti += 1;
            this.pareggi++;
        } else {
            this.sconfitte++;
        }
    }

	public Squadra getSquadra() {
		return squadra;
	}

	public void setSquadra(Squadra squadra) {
		this.squadra = squadra;
	}

	public int getPunti() {
		return punti;
	}

	public void setPunti(int punti) {
		this.punti = punti;
	}

	public int getGiocate() {
		return giocate;
	}

	public void setGiocate(int giocate) {
		this.giocate = giocate;
	}

	public int getVittorie() {
		return vittorie;
	}

	public void setVittorie(int vittorie) {
		this.vittorie = vittorie;
	}

	public int getPareggi() {
		return pareggi;
	}

	public void setPareggi(int pareggi) {
		this.pareggi = pareggi;
	}

	public int getSconfitte() {
		return sconfitte;
	}

	public void setSconfitte(int sconfitte) {
		this.sconfitte = sconfitte;
	}
    
}
