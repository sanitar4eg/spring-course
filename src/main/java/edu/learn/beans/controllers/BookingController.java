package edu.learn.beans.controllers;

import edu.learn.beans.models.Ticket;
import edu.learn.beans.models.User;
import edu.learn.beans.services.BookingService;
import edu.learn.beans.services.UserService;
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

	private final BookingService bookingService;
	private final UserService userService;

	@Autowired
	public BookingController(BookingService bookingService, UserService userService) {
		this.bookingService = bookingService;
		this.userService = userService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<Ticket> getTicketsForEvent(@RequestParam String event, @RequestParam String auditorium,
		@RequestParam LocalDateTime date) {
		return bookingService.getTicketsForEvent(event, auditorium, date);
	}

	@RequestMapping(value = "price", method = RequestMethod.GET)
	public double getTicketPrice(@RequestParam String event, @RequestParam String auditorium,
		@RequestParam LocalDateTime date, @RequestParam List<Integer> seats, @RequestParam Long userId) {
		User user = userService.getById(userId);
		return bookingService.getTicketPrice(event, auditorium, date, seats, user);
	}

	@RequestMapping(value = "bookTicket", method = RequestMethod.GET)
	public Ticket getTicketsForEvent(@RequestParam Long userId, @RequestParam Ticket ticket) {
//		User user = userService.getById(userId);
//		return bookingService.bookTicket(user, ticket);
//		TODO: Can't get ticket by id
		return null;
	}

}
