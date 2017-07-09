package edu.learn.beans.controllers;

import edu.learn.beans.models.Ticket;
import edu.learn.beans.models.User;
import edu.learn.beans.services.BookingService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("booking")
public class BookingController {

	final BookingService bookingService;

	@Autowired
	public BookingController(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<Ticket> getTicketsForEvent(@RequestParam String event, @RequestParam String auditorium,
		@RequestParam LocalDateTime date) {
		return bookingService.getTicketsForEvent(event, auditorium, date);
	}

	@RequestMapping(value = "price", method = RequestMethod.GET)
	public double getTicketPrice(@RequestParam String event, @RequestParam String auditorium,
		@RequestParam LocalDateTime date, @RequestParam List<Integer> seats, @RequestParam User user) {
		return bookingService.getTicketPrice(event, auditorium, date, seats, user);
	}

	@RequestMapping(value = "bookTicket", method = RequestMethod.GET)
	public Ticket getTicketsForEvent(@RequestParam User user, @RequestParam Ticket ticket) {
		return bookingService.bookTicket(user, ticket);
	}

}
