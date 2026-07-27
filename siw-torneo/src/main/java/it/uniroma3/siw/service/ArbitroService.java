package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Arbitro;
import it.uniroma3.siw.repository.ArbitroRepository;

@Service
public class ArbitroService {
	private ArbitroRepository arbitroRepository;
	public ArbitroService(ArbitroRepository arbitroRepository) {
		this.arbitroRepository=arbitroRepository;
	}
	 @Transactional(readOnly = true)
	    public List<Arbitro> findAll() {
	        return (List<Arbitro>) this.arbitroRepository.findAll();
	    }

	    @Transactional(readOnly = true)
	    public Arbitro findById(Long id) {
	        return this.arbitroRepository.findById(id).get();
	    }

	    @Transactional
	    public Arbitro save(Arbitro arbitro) {
	        return this.arbitroRepository.save(arbitro);
	    }
}


