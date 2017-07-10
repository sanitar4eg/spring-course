package edu.learn.beans.controllers;

import edu.learn.beans.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("view/event")
public class EventViewController {

	private final EventService eventService;

	@Autowired
	public EventViewController(EventService eventService) {
		this.eventService = eventService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getAll(Model model) {
		model.addAttribute("events", eventService.getAll());
		return "events";
	}

}
