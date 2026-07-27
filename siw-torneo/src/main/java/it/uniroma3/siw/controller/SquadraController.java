package it.uniroma3.siw.controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.Torneo;
import it.uniroma3.siw.service.SquadraService;
import it.uniroma3.siw.service.TorneoService;
import jakarta.validation.Valid;

@Controller
public class SquadraController {
	
	private final SquadraService squadraService;
	private TorneoService torneoService;
	public SquadraController(SquadraService squadraService,TorneoService torneoService) {
        this.squadraService = squadraService;
        this.torneoService=torneoService;
    }
	

	@GetMapping("/tornei/{torneoId}/squadre/{squadraId}")
	public String getDettaglioSquadraDaTorneo(@PathVariable("torneoId") Long torneoId, 
	                                          @PathVariable("squadraId") Long squadraId, 
	                                          Model model) {
	    // 1. Recuperiamo la squadra e lo specifico torneo dal database
	    Squadra squadra = this.squadraService.findById(squadraId);
	    Torneo torneo = this.torneoService.findById(torneoId);
	    
	    // 2. Passiamo ENTRAMBI gli oggetti a Thymeleaf
	    model.addAttribute("squadra", squadra);
	    model.addAttribute("torneo", torneo); // 🔥 Questo eviterà l'errore "Property 'id' cannot be found on null"
	    
	    // 3. Ritorniamo la pagina dei dettagli che andava in crash
	    return "squadre/dettaglioSquadra";
	}	
	@GetMapping("/admin/squadre/{torneoId}/new")
	public String formNuovaSquadra(@PathVariable("torneoId") Long torneoId, Model model) {
	    model.addAttribute("squadra", new Squadra());
	    model.addAttribute("torneoId", torneoId);
	    return "admin/formNuovaSquadra";
	}
	@PostMapping("/admin/squadre/{torneoId}/new")
	public String save(@PathVariable("torneoId") Long torneoId,
						@Valid @ModelAttribute("squadra") Squadra squadra, BindingResult bindingResult,
						Model model) {

			if (bindingResult.hasErrors()) {
	            model.addAttribute("torneoId", torneoId); 
	            return "admin/formNuovaSquadra";
	        } 
		  Torneo torneo = this.torneoService.findById(torneoId);
		  squadra.getTorneos().add(torneo);
		  torneo.getSquadre().add(squadra);
		  this.squadraService.save(squadra);
		  return "redirect:/tornei/" + torneoId + "/partecipanti";
	}
	
	@GetMapping("/admin/tornei/{torneoId}/squadre/{squadraId}/edit")
	public String formModificaSquadra(@PathVariable("torneoId") Long torneoId, 
	                                  @PathVariable("squadraId") Long squadraId, 
	                                  Model model) {
	    Squadra squadra = this.squadraService.findById(squadraId);
	    
	    model.addAttribute("squadra", squadra);
	    model.addAttribute("torneoId", torneoId); // Lo passiamo per usarlo nel tasto "Annulla" o nel form
	    
	    return "admin/formModificaSquadra";
	}

    // 4. Salva le modifiche apportate alla squadra
    // Nota: Se l'oggetto squadra ha già un ID compilato, l'operazione .save() di JPA farà un UPDATE automatico invece di un INSERT!
	@PostMapping("/admin/tornei/{torneoId}/squadre/{squadraId}/edit")
	public String aggiornaSquadra(@PathVariable("torneoId") Long torneoId,
	                              @PathVariable("squadraId") Long squadraId,
	                              @Valid @ModelAttribute("squadra") Squadra squadra, 
	                              BindingResult bindingResult, Model model) {
	    
	    if (bindingResult.hasErrors()) {
	    	 model.addAttribute("torneoId", torneoId);
	        return "admin/formModificaSquadra";
	    }
	    
	    // Salvataggio semplice e diretto
	    this.squadraService.save(squadra); 
	    
	    // Redirect pulito: torniamo al dettaglio di QUELLA squadra in QUEL torneo
	    return "redirect:/tornei/" + torneoId + "/squadre/" + squadraId;
	}
 
    @PostMapping("/admin/tornei/{torneoId}/rimuovi-squadra/{squadraId}")
    public String deleteSquadra(@PathVariable("torneoId") Long torneoId, 
                                @PathVariable("squadraId") Long squadraId) {

        // 2. Recuperiamo lo specifico torneo e la specifica squadra
        Torneo torneo = this.torneoService.findById(torneoId);
        Squadra squadra = this.squadraService.findById(squadraId);

            // 3. 🔥 RIMUOVIAMO LA SQUADRA SOLO DA QUESTO TORNEO
            // Essendo Torneo il proprietario, la togliamo dalla sua lista.
            torneo.getSquadre().remove(squadra);
            // 4. Salviamo il torneo: Hibernate cancellerà SOLO la riga di collegamento nella tabella di mezzo
            this.torneoService.save(torneo);
            return "redirect:/tornei/" + torneoId;
    }
}
