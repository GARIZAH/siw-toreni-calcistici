package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Arbitro;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Partita;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.Torneo;
import it.uniroma3.siw.service.ArbitroService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.PartitaService;
import it.uniroma3.siw.service.SquadraService;
import it.uniroma3.siw.service.TorneoService;

@Controller
public class PartitaController {
	private PartitaService partitaService;
    private CredentialsService credentialsService;
    private TorneoService torneoService;
    private SquadraService squadraService;
    private ArbitroService arbitroService;

    public PartitaController(PartitaService partitaService, CredentialsService credentialsService,
                              TorneoService torneoService, SquadraService squadraService,
                              ArbitroService arbitroService) {
        this.partitaService = partitaService;
        this.credentialsService = credentialsService;
        this.torneoService = torneoService;
        this.squadraService = squadraService;
        this.arbitroService = arbitroService;
    }
	@GetMapping("/partite/{id}")
	public String mostraPartita(@PathVariable Long id, Model model,
								@RequestParam(value = "editCommentoId", required = false) Long editCommentoId,
								@AuthenticationPrincipal UserDetails userDetails) {
	    
		Partita partita = this.partitaService.findById(id);
	    model.addAttribute("partita", partita);
	    if (userDetails != null) {
	        Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
	        // FONDAMENTALE: Devi passare l'utente loggato con lo stesso nome usato nell'HTML
	        model.addAttribute("utenteLoggato", credentials.getUtente());
	        model.addAttribute("isAdmin", Credentials.ADMIN_ROLE.equals(credentials.getRole()));
	    }
	    model.addAttribute("idCommentoInModifica", editCommentoId);
	    // Non serve fare altro! I commenti sono già dentro l'oggetto partita grazie a JPA
	    return "partite/dettaglioPartita"; 
	}
	//admin aggiunge partita
	@GetMapping("/admin/tornei/{id}/partite/new")
	public String formNuovaPartita(@PathVariable("id")Long id, Model model) {
		Torneo torneo=this.torneoService.findById(id);
		List<Arbitro> arbitri=this.arbitroService.findAll();
		List<Squadra> squadre=this.squadraService.findALL();
		model.addAttribute("partita",new Partita());
		model.addAttribute("torneo",torneo);
		model.addAttribute("squadre",squadre);
		model.addAttribute("arbitri",arbitri);
		return"admin/formNuovaPartita";
	}
	@PostMapping("/admin/tornei/{id}/partite/new")
	public String salvaPartita(@PathVariable("id")Long torneoId,
								@ModelAttribute("partita") Partita partita,
								@RequestParam("squadraHome") Long squadraHome,
								@RequestParam("squadraAway") Long squadraAway,
								@RequestParam(value="arbitro", required=false)Long arbitro,Model model){
		if (squadraHome.equals(squadraAway)) {
			model.addAttribute("erroreSquadre", "Errore: Una squadra non può giocare contro se stessa!");
			model.addAttribute("torneo", this.torneoService.findById(torneoId));
			model.addAttribute("squadre", this.squadraService.findALL());
			model.addAttribute("arbitri", this.arbitroService.findAll());
			return "admin/formNuovaPartita";
		}
		Torneo torneo=this.torneoService.findById(torneoId);
		Squadra home=this.squadraService.findById(squadraHome);
		Squadra away=this.squadraService.findById(squadraAway);
		partita.setId(null);
		partita.setTorneo(torneo);
		partita.setSquadraAway(away);
		partita.setSquadraHome(home);
		
        if (arbitro != null) {
            partita.setArbitro(this.arbitroService.findById(arbitro));
        }
        this.partitaService.save(partita);
        return "redirect:/tornei/" + torneoId + "/calendario";
	}
	//admin inserisic/aggiorna nuovo risultato
	@GetMapping("/admin/partite/{id}/risultato")
		public String formRisultato(@PathVariable("id")Long id, Model model) {
			Partita partita=this.partitaService.findById(id);
			model.addAttribute("partita",partita);
			return "partite/risultato";
		}
	@PostMapping("/admin/partite/{id}/risultato")
	public String salvaRIusltato(@PathVariable("id")Long id,
								@RequestParam("goalsHome")Integer goalsHome,
								@RequestParam("goalsAway")Integer goalsAway) {
		Partita partita=this.partitaService.findById(id);
		partita.setGoalsAway(goalsAway);
		partita.setGoalsHome(goalsHome);
		this.partitaService.save(partita);
		return "redirect:/partite/" + id;
	}
	//admin elimina partita
	@PostMapping("/admin/partite/{id}/elimina")
	public String eliminaPartita(@PathVariable("id") Long id) {
	    // 1. Recuperiamo la partita prima di cancellarla per sapere a quale torneo apparteneva
	    Partita partita = this.partitaService.findById(id);
	    Long torneoId = partita.getTorneo().getId();
	    
	    // 2. Eliminiamo la partita
	    this.partitaService.deleteById(id);
	    
	    // 3. 🔥 REDIRECT CORRETTO: Torna al calendario del torneo specifico
	    return "redirect:/tornei/" + torneoId + "/calendario";
	}
												
}
