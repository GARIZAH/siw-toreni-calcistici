package it.uniroma3.siw.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Giocatore;
import it.uniroma3.siw.repository.GiocatoreRepository;


@Service
public class GiocatoreService {
	private GiocatoreRepository giocatoreRepository;
	public GiocatoreService(GiocatoreRepository giocatoreRepository) {
		this.giocatoreRepository=giocatoreRepository;
	}
	@Transactional(readOnly=true)
	public Giocatore findById(Long id) {
		return this.giocatoreRepository.findById(id).get();
	}
	@Transactional
	public Giocatore save( Giocatore giocatore) {
		return this.giocatoreRepository.save(giocatore);
		
	}
	@Transactional
	public void deleteById(Long id) {
		this.giocatoreRepository.deleteById(id);
		
	}
}
