package it.uniroma3.siw.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Commento;
import it.uniroma3.siw.model.Partita;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.CommentoRepository;

@Service
public class CommentoService {
	private CommentoRepository commentoRepository;
	public CommentoService(CommentoRepository commentoRepository) {
		this.commentoRepository=commentoRepository;
	}
	@Transactional(readOnly=true)
	public Commento findById(Long id) {
		return this.commentoRepository.findById(id).get();
		}
	@Transactional
	public Commento save(Commento nuovoCommento) {
		return this.commentoRepository.save(nuovoCommento);
		
	}
	@Transactional
	public void aggiungiCommento(String testo, Partita partita, Utente utente) {
		Commento nuovoCommento=new Commento();
		nuovoCommento.setTesto(testo);
		nuovoCommento.setPartita(partita);
		nuovoCommento.setUtente(utente);
		this.commentoRepository.save(nuovoCommento);
		
	}
	
}
