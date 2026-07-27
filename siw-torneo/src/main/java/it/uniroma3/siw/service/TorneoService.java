package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		
}