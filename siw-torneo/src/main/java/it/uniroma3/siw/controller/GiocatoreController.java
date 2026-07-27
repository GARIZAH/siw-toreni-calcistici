package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.Giocatore;
import it.uniroma3.siw.model.GiocatoreRuolo;
import it.uniroma3.siw.service.GiocatoreService;
import it.uniroma3.siw.service.SquadraService;
import jakarta.validation.Valid;

@Controller
public class GiocatoreController {
	private GiocatoreService giocatoreService;
	private SquadraService squadraService;
	public GiocatoreController(GiocatoreService giocatoreService,SquadraService squadraService) {
		this.giocatoreService=giocatoreService;
		this.squadraService=squadraService;
	}
	@GetMapping("/admin/squadre/{id}/giocatori/new")
	public String formNuovoGiocatore(@PathVariable("id")Long id,Model model) {
		Squadra squadra=this.squadraService.findById(id);
		model.addAttribute("squadra",squadra);
		  model.addAttribute("torneo", squadra.getTorneos().get(0));
		model.addAttribute("giocatore",new Giocatore());
		model.addAttribute("ruoli",GiocatoreRuolo.values());
		return"admin/formNuovoGiocatore";
	}
	@PostMapping("/admin/squadre/{id}/giocatori/new")
	public String salvaGiocatore(@PathVariable("id")Long id,
									@Valid@ModelAttribute("giocatore")Giocatore giocatore,
									BindingResult bindingResult,Model model) {
		if (bindingResult.hasErrors()) {
	        model.addAttribute("squadraId", id);
	        return "admin/formNuovoGiocatore";
	    }

	    // 1. IMPORTANTE: Forza l'ID del giocatore a null per essere sicuro che sia una NUOVA creazione
	    giocatore.setId(null);

	    // 2. Recuperiamo la squadra dal database usando l'ID passato nell'URL
	    Squadra squadra = this.squadraService.findById(id);
	    
	    if (squadra != null) {
	        // 3. Colleghiamo il giocatore alla squadra
	        giocatore.setSquadra(squadra);
	        
	        // 4. Salva il giocatore nel database (Genererà un ID tutto suo, es. 153, 154...)
	        this.giocatoreService.save(giocatore);
	    }
	    Long torneoId = squadra.getTorneos().get(0).getId();
	    
	    // 6. Ritorna al dettaglio della squadra con l'URL corretto del torneo
	    return "redirect:/tornei/" + torneoId + "/squadre/" + id;
	}
	
	@GetMapping("/admin/giocatori/{id}/edit")
	public String formModificaGiocatore(@PathVariable("id") Long id, Model model)	{
		Giocatore giocatore=this.giocatoreService.findById(id);
		model.addAttribute("giocatore",giocatore);
		model.addAttribute("squadra",giocatore.getSquadra());
		model.addAttribute("ruoli",GiocatoreRuolo.values());
		return "admin/formModificaGiocatore";
		
	}
	@PostMapping("/admin/giocatori/{id}/edit")
	public String salvaModifica(@PathVariable("id") Long id,Model model,
			@Valid@ModelAttribute("giocatore")Giocatore giocatoreModificato,
			BindingResult bindingResult) {
		Giocatore giocatoreOriginale=this.giocatoreService.findById(id);
		if (bindingResult.hasErrors()) {
	        model.addAttribute("squadra", giocatoreOriginale.getSquadra());
	        model.addAttribute("ruoli", GiocatoreRuolo.values());
	        return "admin/formModificaGiocatore";
	    }
		giocatoreOriginale.setNome(giocatoreModificato.getNome());
		giocatoreOriginale.setCognome(giocatoreModificato.getCognome());
		giocatoreOriginale.setDataDiNascita(giocatoreModificato.getDataDiNascita());
		giocatoreOriginale.setAltezza(giocatoreModificato.getAltezza());
		giocatoreOriginale.setRuolo(giocatoreModificato.getRuolo());

		this.giocatoreService.save(giocatoreOriginale);
		Squadra squadra = giocatoreOriginale.getSquadra();
		Long torneoId = squadra.getTorneos().get(0).getId();
		return "redirect:/tornei/" + torneoId + "/squadre/" + squadra.getId();
	}
	@GetMapping("/admin/giocatori/{id}/delete")
	public String eliminaGiocatore(@PathVariable("id") Long id,Model model) {
		Giocatore giocatore=this.giocatoreService.findById(id);
		if (giocatore == null) {
	        return "redirect:/tornei"; 
	    }
		Long squadraId = giocatore.getSquadra().getId();
	    
	    // 2. Recuperiamo l'ID del primo torneo associato a questa squadra
	    Long torneoId = giocatore.getSquadra().getTorneos().get(0).getId();
	    
	    // 3. Eliminiamo il giocatore dal database
	    this.giocatoreService.deleteById(id);
	    
	    // 4. Redirect perfetto all'URL con il torneo!
	    return "redirect:/tornei/" + torneoId + "/squadre/" + squadraId;
	}
			
}	
