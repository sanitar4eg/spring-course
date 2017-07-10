package edu.learn.beans.controllers;

import edu.learn.beans.services.AuditoriumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("view/auditorium")
public class AuditoriumViewController {

	private final AuditoriumService auditoriumService;

	@Autowired
	public AuditoriumViewController(AuditoriumService auditoriumService) {
		this.auditoriumService = auditoriumService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getAuditoriums(Model model) {
		model.addAttribute("auditoriums", auditoriumService.getAuditoriums());
		return "auditoriums";
	}

	@RequestMapping(method = RequestMethod.GET, value = "{name}")
	public String getAuditoriums(Model model, @PathVariable String name) {
		model.addAttribute("auditorium", auditoriumService.getByName(name));
		return "auditorium";
	}

}
