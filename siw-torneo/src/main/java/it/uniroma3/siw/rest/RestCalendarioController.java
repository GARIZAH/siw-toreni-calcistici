package it.uniroma3.siw.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import it.uniroma3.siw.model.Partita;
import it.uniroma3.siw.service.TorneoService;
 
@RestController
@RequestMapping("/rest/tornei")
@CrossOrigin(origins = "http://localhost:5173")
public class RestCalendarioController {
 
    private final TorneoService torneoService;
 
    public RestCalendarioController(TorneoService torneoService) {
        this.torneoService = torneoService;
    }
 
    // GET /rest/tornei/{id}/calendario  →  lista partite del torneo
    @GetMapping("/{id}/calendario")
    public ResponseEntity<List<Partita>> calendario(@PathVariable Long id) {
        return torneoService.findById(id) != null
            ? ResponseEntity.ok(torneoService.findById(id).getPartite())
            : ResponseEntity.notFound().build();
    }
}
 