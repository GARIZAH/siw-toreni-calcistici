package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.model.Torneo;
import it.uniroma3.siw.service.TorneoService;

@Controller
public class HomeController {
	private TorneoService torneoService;

	public HomeController(TorneoService torneoService) {
		this.torneoService = torneoService;
	}

	@GetMapping("/")
	public String getHome(Model model) {
		List<Torneo> tornei = this.torneoService.findALL();
		model.addAttribute("tornei", tornei);
		return "index";
	}

	@GetMapping("/admin")
	public String getAdminDashboard() {
		return "admin/index";
	}
}