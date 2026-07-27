package it.uniroma3.siw.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Commento;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Partita;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CommentoService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.PartitaService;

@Controller
public class CommentoController {
		private CommentoService commentoService;
		private final PartitaService partitaService;
		private CredentialsService credentialsService;
		public CommentoController(CommentoService commentoService,PartitaService partitaService,CredentialsService credentialsService) {
			this.commentoService=commentoService;
			this.credentialsService=credentialsService;
			this.partitaService=partitaService;
		}
		
		@PostMapping("/registrato/partite/{partitaId}/commenti")
	    public String inserisciCommento(@PathVariable("partitaId") Long partitaId,
	    								@RequestParam("testo") String testo,
	                                    @AuthenticationPrincipal UserDetails userDetails) {
	        
	       
	        Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());
	        Utente utente = credentials.getUtente();

	        Partita partita = partitaService.findById(partitaId);
	        commentoService.aggiungiCommento(testo, partita, utente);

	     
	        return "redirect:/partite/" + partitaId;
	    }

	   

	    // POST MODIFICA PROPRIO COMMENTO 
	    @PostMapping("/registrato/commenti/edit/{id}")
	    public String aggiornaCommento(@PathVariable("id") Long id,
	                                   @RequestParam("testo") String nuovoTesto,
	                                   @AuthenticationPrincipal UserDetails userDetails) {
	        
	        Commento commento = this.commentoService.findById(id);
	        Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());

	        // Altro controllo di sicurezza prima di salvare su DB
	        if (!commento.getUtente().getId().equals(credentials.getUtente().getId())) {
	            return "redirect:/partite/" + commento.getPartita().getId() + "?errore=non_autorizzato";
	        }

	        commento.setTesto(nuovoTesto);
	        this.commentoService.save(commento);

	        // Ritorna alla partita originaria
	        return "redirect:/partite/" + commento.getPartita().getId();
	    }
}
