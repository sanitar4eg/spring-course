package edu.learn.beans.controllers;

import edu.learn.beans.models.Ticket;
import edu.learn.beans.services.BookingService;
import edu.learn.beans.services.UserService;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("view/booking")
public class BookingViewController {

	private final BookingService bookingService;

	@Autowired
	public BookingViewController(BookingService bookingService, UserService userService) {
		this.bookingService = bookingService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getTicketsForEvent(@RequestParam String event, @RequestParam String auditorium,
		@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date, Model model) {
		List<Ticket> tickets = bookingService.getTicketsForEvent(event, auditorium, date);
		Ticket ticket = tickets.stream().findFirst()
			.orElseThrow(() -> new IllegalStateException("Event have not tickets"));
		model.addAttribute("tickets", bookingService.getTicketsForEvent(event, auditorium, date));
		model.addAttribute("event", ticket.getEvent());
		model.addAttribute("auditorium", ticket.getEvent().getAuditorium());

		return "tickets";
	}

	@RequestMapping(method = RequestMethod.GET, headers = "Accept=application/pdf")
	public ModelAndView getTicketsForEventViaPDF(@RequestParam String event, @RequestParam String auditorium,
		@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime date) {
		List<Ticket> tickets = bookingService.getTicketsForEvent(event, auditorium, date);
		Ticket ticket = tickets.stream().findFirst()
			.orElseThrow(() -> new IllegalStateException("Event have not tickets"));
		Map<String, Object> model = new HashMap<>();

		model.put("tickets", bookingService.getTicketsForEvent(event, auditorium, date));
		model.put("event", ticket.getEvent());
		model.put("auditorium", ticket.getEvent().getAuditorium());

		return new ModelAndView("pdfTicketsView", model);
	}

}
