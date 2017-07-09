package edu.learn;

import edu.learn.beans.aspects.CounterAspect;
import edu.learn.beans.aspects.DiscountAspect;
import edu.learn.beans.aspects.LuckyWinnerAspect;
import edu.learn.beans.models.Auditorium;
import edu.learn.beans.models.Event;
import edu.learn.beans.models.Rate;
import edu.learn.beans.models.Ticket;
import edu.learn.beans.models.User;
import edu.learn.beans.services.AuditoriumService;
import edu.learn.beans.services.BookingService;
import edu.learn.beans.services.DiscountService;
import edu.learn.beans.services.EventService;
import edu.learn.beans.services.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup {

	private final AuditoriumService auditoriumService;
	private final BookingService bookingService;
	private final EventService eventService;
	private final UserService userService;
	private final DiscountService discountService;

	@Autowired
	public ApplicationStartup(AuditoriumService auditoriumService, BookingService bookingService,
		EventService eventService, UserService userService, DiscountService discountService) {
		this.auditoriumService = auditoriumService;
		this.bookingService = bookingService;
		this.eventService = eventService;
		this.userService = userService;
		this.discountService = discountService;
	}

	@PostConstruct
	public void init() {

		String email = "dmitriy.vbabichev@gmail.com";
		String name = "Dmytro Babichev";
		String eventName = "The revenant";
		String auditoriumName = "Blue hall";
		Auditorium blueHall = auditoriumService.getByName(auditoriumName);
		Auditorium yellowHall = auditoriumService.getByName("Yellow hall");
		Auditorium redHall = auditoriumService.getByName("Red hall");
		LocalDateTime dateOfEvent = LocalDateTime.of(LocalDate.of(2016, 2, 5), LocalTime.of(15, 45, 0));

		userService.register(new User(email, name, LocalDate.now()));
		userService.register(new User("laory@yandex.ru", name, LocalDate.of(1992, 4, 29)));

		User userByEmail = userService.getUserByEmail(email);
		System.out.println("User with email: [" + email + "] is " + userByEmail);
		System.out.println();

		System.out.println("All users with name: [" + name + "] are: ");
		userService.getUsersByName(name).forEach(System.out::println);
		System.out.println();

		Event event1 = eventService.create(
			new Event(eventName, Rate.HIGH, 60, LocalDateTime.of(LocalDate.of(2016, 2, 5), LocalTime.of(9, 0, 0)),
				blueHall));
		System.out.println();
		System.out.println("Event by name: " + eventService.getByName(event1.getName()));
		System.out.println();
		eventService.create(new Event(eventName, Rate.HIGH, 60, dateOfEvent, blueHall));
		Event event2 = eventService.create(
			new Event(eventName, Rate.HIGH, 60, LocalDateTime.of(LocalDate.of(2016, 2, 5), LocalTime.of(21, 18, 0)),
				blueHall));
		eventService.create(
			new Event(eventName, Rate.HIGH, 90, LocalDateTime.of(LocalDate.of(2016, 2, 5), LocalTime.of(21, 18, 0)),
				redHall));
		Event event = new Event(eventName, Rate.HIGH, 150,
			LocalDateTime.of(LocalDate.of(2016, 2, 5), LocalTime.of(21, 18, 0)), yellowHall);
		event = eventService.create(event);

		System.out.println("List of all events:");
		eventService.getAll().forEach(System.out::println);
		System.out.println();

		System.out.println(
			"Discount for user: [" + email + "] for event: [" + eventName + "] in auditorium: [" + auditoriumName +
				"] on date: [" + dateOfEvent + "] is [" +
				discountService.getDiscount(userByEmail, eventService.getEvent(eventName, blueHall, dateOfEvent))
				+ "]");
		System.out.println();

		eventService.remove(event2);
		System.out.println("List of all events:");
		eventService.getAll().forEach(System.out::println);
		System.out.println();

		List<Integer> seats = Arrays.asList(23, 24, 25, 26);
		double ticketPrice = bookingService.getTicketPrice(event.getName(), event.getAuditorium().getName(),
			event.getDateTime(), seats, userByEmail);
		System.out.println("Price for event: [" + event + "], seats: [" + seats + "], user: [" + userByEmail + "] = "
			+ ticketPrice);

		List<Integer> seats2 = Arrays.asList(27, 28, 29, 30);
		List<Integer> seats3 = Arrays.asList(2, 8, 9, 3);
		bookingService.bookTicket(userByEmail, new Ticket(event, LocalDateTime.now(), seats, userByEmail, ticketPrice));
		bookingService.bookTicket(userByEmail, new Ticket(event, LocalDateTime.now(), seats2, userByEmail,
			bookingService.getTicketPrice(event.getName(),
				event.getAuditorium().getName(),
				event.getDateTime(), seats2,
				userByEmail)));
		bookingService.bookTicket(userByEmail, new Ticket(event, LocalDateTime.now(), seats3, userByEmail,
			bookingService.getTicketPrice(event.getName(),
				event.getAuditorium().getName(),
				event.getDateTime(), seats3,
				userByEmail)));

		System.out.println();
		System.out.println("Tickets booked for event: [" + event + "]");
		List<Ticket> ticketsForEvent = bookingService.getTicketsForEvent(event.getName(),
			event.getAuditorium().getName(),
			event.getDateTime());
		IntStream.range(0, ticketsForEvent.size()).forEach(
			i -> System.out.println("" + i + ") " + ticketsForEvent.get(i)));

		System.out.println();
		eventService.getByName("testName1");
		eventService.getByName("testName2");
		eventService.getByName("testName2");
		eventService.getByName("testName3");
		eventService.getByName(eventName);
		eventService.getEvent(event.getName(), event.getAuditorium(), event.getDateTime());

		bookingService.getTicketPrice(event.getName(), event.getAuditorium().getName(), event.getDateTime(), seats,
			userByEmail);
		bookingService.getTicketPrice(event.getName(), event.getAuditorium().getName(), event.getDateTime(), seats,
			userByEmail);

		System.out.println("CounterAspect.getAccessByNameStat() = " + CounterAspect.getAccessByNameStat());
		System.out.println("CounterAspect.getGetPriceByNameStat() = " + CounterAspect.getGetPriceByNameStat());
		System.out.println("CounterAspect.getBookTicketByNameStat() = " + CounterAspect.getBookTicketByNameStat());
		System.out.println();
		System.out.println("DiscountAspect.getDiscountStatistics() = " + DiscountAspect.getDiscountStatistics());
		System.out.println();
		System.out.println("LuckyWinnerAspect.getLuckyUsers() = " + LuckyWinnerAspect.getLuckyUsers());
	}
}
