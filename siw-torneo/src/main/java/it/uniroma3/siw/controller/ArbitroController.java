package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Arbitro;
import it.uniroma3.siw.service.ArbitroService;
import jakarta.validation.Valid;

@Controller
public class ArbitroController {

    
    private ArbitroService arbitroService;
    public ArbitroController(ArbitroService arbitroService) {
    	this.arbitroService=arbitroService;
    }

    // 1. Mostra la lista di tutti gli arbitri (Accessibile a tutti)
    @GetMapping("/arbitri")
    public String list(Model model) {
        model.addAttribute("arbitri", this.arbitroService.findAll());
        return "arbitri/list";
    }

  
}
