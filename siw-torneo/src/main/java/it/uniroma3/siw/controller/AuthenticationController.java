package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Credentials;

import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UtenteService;
import jakarta.validation.Valid;


@Controller
public class AuthenticationController {

	private CredentialsService credentialsService;
	private UtenteService userService;

public AuthenticationController(CredentialsService credentialsService,UtenteService userService) {
	this.credentialsService=credentialsService;
	this.userService=userService;
}
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("utente",new Utente());
		model.addAttribute("credentials",new Credentials());
		return "formRegisterUser";
	}
	@GetMapping("/login")
	public String showLoginForm(Model model) {
		return "formLogin";
	}
	@GetMapping("/success")
	public String defaultAfterLogin(Model model) {
		return "index";
	}
	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute("credentials") Credentials credentials,
	             				BindingResult credentialsBindingResult,
	             				Model model) {

	    // Se ci sono errori nei form, ricarica la pagina di registrazione
	    if (credentialsBindingResult.hasErrors()) {
	        return "formRegisterUser";
	    }

	    credentialsService.saveCredentials(credentials);
	    return "registrationSuccessful";
	    
	}
	
}
