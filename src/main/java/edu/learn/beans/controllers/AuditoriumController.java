package edu.learn.beans.controllers;

import edu.learn.beans.models.Auditorium;
import edu.learn.beans.services.AuditoriumService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auditorium")
public class AuditoriumController {

	final AuditoriumService auditoriumService;

	@Autowired
	public AuditoriumController(AuditoriumService auditoriumService) {
		this.auditoriumService = auditoriumService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<Auditorium> getAuditoriums() {
		return auditoriumService.getAuditoriums();
	}

	@RequestMapping(method = RequestMethod.GET, params = "name")
	public Auditorium getByName(@RequestParam String name) {
		return auditoriumService.getByName(name);
	}

	@RequestMapping(value = "getSeats", method = RequestMethod.GET)
	public int getSeatsNumber(@RequestParam String name) {
		return auditoriumService.getSeatsNumber(name);
	}

	@RequestMapping(value = "getVipSeats", method = RequestMethod.GET)
	public List<Integer> getVipSeats(@RequestParam String name) {
		return auditoriumService.getVipSeats(name);
	}

}
