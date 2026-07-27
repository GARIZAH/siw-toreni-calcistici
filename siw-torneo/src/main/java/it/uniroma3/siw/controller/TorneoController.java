package it.uniroma3.siw.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.dto.Classifica;

import it.uniroma3.siw.model.Partita;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.Torneo;
import it.uniroma3.siw.service.SquadraService;
import it.uniroma3.siw.service.TorneoService;
import jakarta.validation.Valid;

@Controller
public class TorneoController {
	private TorneoService torneoService;
	private SquadraService squadraService;

	public TorneoController(TorneoService torneoService, SquadraService squadraService) {
		this.torneoService = torneoService;
		this.squadraService = squadraService;
	}

	@GetMapping("/tornei/{id}")
	public String show(@PathVariable("id") Long id, Model model) {
		Torneo torneo = this.torneoService.findById(id);
		model.addAttribute("torneo", torneo);
		return "tornei/show.html";
	}

	@GetMapping("/tornei")
	public String elenco(Model model) {
		List<Torneo> elencoTorneo = this.torneoService.findALL();
		model.addAttribute("tornei", elencoTorneo);

		return "tornei/elenco.html";
	}

	@GetMapping("/tornei/{id}/partecipanti")
	public String partecipanti(@PathVariable("id") Long id, Model model) {
		Torneo torneo = this.torneoService.findById(id);
		model.addAttribute("torneo", torneo);

		return "tornei/partecipanti.html";

	}

	@GetMapping("/tornei/{id}/calendario")
	public String calendario(@PathVariable("id") Long id, Model model) {
		Torneo torneo = this.torneoService.findById(id);
		model.addAttribute("torneo", torneo);
		return "tornei/calendario.html";

	}

	@GetMapping("/tornei/{id}/classifica")
	public String classifica(@PathVariable("id") Long id, Model model) {
		Torneo torneo = this.torneoService.findById(id);
		List<Classifica> classifica = new ArrayList<>();
		// Per ogni squadra del torneo calcoliamo le statistiche
		for (Squadra s : torneo.getSquadre()) {
			Classifica riga = new Classifica(s);

			// 1. Controlliamo le partite giocate in casa
			if (s.getPartiteHome() != null) {
				for (Partita p : s.getPartiteHome()) {
					// Calcoliamo i punti solo se la partita è terminata ed appartiene a questo
					// torneo
					if (p.getTorneo().equals(torneo) && p.getGoalsHome() != null && p.getGoalsAway() != null) {
						riga.aggiungiRisultato(p.getGoalsHome(), p.getGoalsAway());
					}
				}
			}

			// 2. Controlliamo le partite giocate in trasferta
			if (s.getPartiteAway() != null) {
				for (Partita p : s.getPartiteAway()) {
					if (p.getTorneo().equals(torneo) && p.getGoalsAway() != null && p.getGoalsHome() != null) {
						riga.aggiungiRisultato(p.getGoalsAway(), p.getGoalsHome());
					}
				}
			}

			classifica.add(riga);
		}

		// Ordiniamo la classifica per punti in ordine decrescente
		classifica.sort((s1, s2) -> Integer.compare(s2.getPunti(), s1.getPunti()));

		model.addAttribute("torneo", torneo);
		model.addAttribute("classifica", classifica);
		return "tornei/classifica.html";
	}


	//ADMIN
	
	@GetMapping("/admin/tornei/{id}/modifica")
	public String formModifica(@PathVariable("id") Long id,Model model) {
		Torneo torneo=this.torneoService.findById(id);
		model.addAttribute("torneo",torneo);
		return "admin/formModificaTorneo";
	}
	@PostMapping("/admin/tornei/{id}/modifica")
	public String update(@PathVariable("id") Long id, 
	                      @Valid @ModelAttribute("torneo") Torneo torneoForm, BindingResult bindingResult) {
	    if (bindingResult.hasErrors()) {
	        return "admin/formModificaTorneo";
	    }
	    Torneo torneoEsistente = this.torneoService.findById(id);
	    torneoEsistente.setNome(torneoForm.getNome());
	    torneoEsistente.setAnno(torneoForm.getAnno());
	    torneoEsistente.setDescrizione(torneoForm.getDescrizione());
	    // NON tocchiamo squadre/partite: restano quelle già caricate da JPA
	    this.torneoService.save(torneoEsistente);
	    return "redirect:/tornei/" + id;
	}
	
	@GetMapping("/admin/tornei/new")
    public String formNuovoTorneo(Model model) {
        model.addAttribute("torneo", new Torneo());
        return "admin/formNuovoTorneo";
    }
	@PostMapping("/admin/tornei/new")
	public String save(@Valid @ModelAttribute("torneo") Torneo torneo, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
            return "admin/formNuovoTorneo";
        }
        this.torneoService.save(torneo);
        return "redirect:/tornei";
	}
	@PostMapping("/admin/tornei/{id}/delete")
	public String deleteTorneo(@PathVariable("id") Long id) {

		Torneo torneo = this.torneoService.findById(id);
	        // 2. 🔥 PULIZIA DEI VINCOLI: Svuotiamo la lista delle squadre iscritte 
	        // per rimuovere in sicurezza le righe dalla tabella di giunzione (torneo_squadre)
	        torneo.getSquadre().clear();
	        this.torneoService.save(torneo);
	        
	        // 3. CANCELLAZIONE: Ora che i vincoli sono sciolti, eliminiamo il torneo.
	        // Nota: Le sue partite verranno cancellate a cascata se hai messo cascade=CascadeType.ALL o REMOVE su "partite"
	        this.torneoService.deleteById(id);
	    

	    // 4. 🔥 RECOUPERATO IL RETURN: Reindirizziamo l'utente alla lista dei tornei aggiornata
	    return "redirect:/tornei";
	}
	

	
	
	
	
}
