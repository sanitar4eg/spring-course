package edu.learn.beans.controllers;

import edu.learn.beans.models.Auditorium;
import edu.learn.beans.models.Event;
import edu.learn.beans.services.AuditoriumService;
import edu.learn.beans.services.EventService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("event")
public class EventController {

	private final EventService eventService;
	private final AuditoriumService auditoriumService;

	@Autowired
	public EventController(EventService eventService, AuditoriumService auditoriumService) {
		this.eventService = eventService;
		this.auditoriumService = auditoriumService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<Event> getAll() {
		return eventService.getAll();
	}

	@RequestMapping(method = RequestMethod.GET, params = "name")
	public List<Event> getByName(@RequestParam String name) {
		return eventService.getByName(name);
	}

	@RequestMapping(method = RequestMethod.GET, params = "name,auditoriumName,date")
	public Event getEvent(@RequestParam String name, @RequestParam String auditoriumName,
		@RequestParam LocalDateTime date) {
		Auditorium auditorium = auditoriumService.getByName(auditoriumName);
		return eventService.getEvent(name, auditorium, date);
	}

	@RequestMapping(method = RequestMethod.GET, params = "from,to")
	public List<Event> getForDateRange(@RequestParam LocalDateTime from, @RequestParam LocalDateTime to) {
		return eventService.getForDateRange(from, to);
	}

	@RequestMapping(method = RequestMethod.GET, params = "to")
	public List<Event> getNextEvents(@RequestParam LocalDateTime to) {
		return eventService.getNextEvents(to);
	}

	@RequestMapping(method = RequestMethod.POST)
	public List<Integer> create(@RequestParam String name) {
//		TODO: Implement
		return null;
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public void remove(@RequestParam String name) {
		List<Event> events = eventService.getByName(name);
		events.forEach(eventService::remove);
	}

	@RequestMapping(method = RequestMethod.PUT, params = "eventName,auditoriumName,date")
	public Event assignAuditorium(@RequestParam String eventName, @RequestParam String auditoriumName,
		@RequestParam LocalDateTime date) {
		Event event = eventService.getByName(eventName).stream().findFirst().orElseThrow(IllegalStateException::new);
		Auditorium auditorium = auditoriumService.getByName(auditoriumName);
		return eventService.assignAuditorium(event, auditorium, date);
	}

}
