package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.dto.Classifica;
import it.uniroma3.siw.model.Partita;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.Torneo;
import it.uniroma3.siw.repository.TorneoRepository;
import jakarta.validation.Valid;


@Service
public class TorneoService {

		private TorneoRepository torneoRepository;
		public TorneoService(TorneoRepository torneoRepository) {
			this.torneoRepository=torneoRepository;
		}
		@Transactional(readOnly = true)
		public List<Torneo> findALL() {
			return (List<Torneo>) this.torneoRepository.findAll();
		}
		@Transactional(readOnly = true)
		public Torneo findById(Long id) {
			return this.torneoRepository.findById(id).get();
			
		}
		@Transactional
		public Torneo save(Torneo torneo) {
			return this.torneoRepository.save(torneo);
			
			
		}
		@Transactional
		public void deleteById(Long id) {
			this.torneoRepository.deleteById(id);
			
		}
		@Transactional(readOnly = true)
		public List<Classifica> calcolaClassifica(Long torneoId) {
			Torneo torneo = this.torneoRepository.findById(torneoId).get();
			List<Classifica> classifica = new ArrayList<>();

			for (Squadra s : torneo.getSquadre()) {
				Classifica riga = new Classifica(s);

				if (s.getPartiteHome() != null) {
					for (Partita p : s.getPartiteHome()) {
						if (p.getTorneo().equals(torneo) && p.getGoalsHome() != null && p.getGoalsAway() != null) {
							riga.aggiungiRisultato(p.getGoalsHome(), p.getGoalsAway());
						}
					}
				}

				if (s.getPartiteAway() != null) {
					for (Partita p : s.getPartiteAway()) {
						if (p.getTorneo().equals(torneo) && p.getGoalsAway() != null && p.getGoalsHome() != null) {
							riga.aggiungiRisultato(p.getGoalsAway(), p.getGoalsHome());
						}
					}
				}

				classifica.add(riga);
			}

			classifica.sort((s1, s2) -> Integer.compare(s2.getPunti(), s1.getPunti()));
			return classifica;
		}
		
}