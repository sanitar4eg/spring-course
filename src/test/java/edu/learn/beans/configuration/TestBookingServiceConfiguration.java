package edu.learn.beans.configuration;

import edu.learn.beans.models.Auditorium;
import edu.learn.beans.models.Booking;
import edu.learn.beans.models.Event;
import edu.learn.beans.models.Rate;
import edu.learn.beans.models.Ticket;
import edu.learn.beans.models.User;
import edu.learn.beans.models.UserAccount;
import edu.learn.beans.repository.AuditoriumRepository;
import edu.learn.beans.repository.BookingRepository;
import edu.learn.beans.repository.EventRepository;
import edu.learn.beans.repository.UserRepository;
import edu.learn.beans.services.AuditoriumService;
import edu.learn.beans.services.BookingService;
import edu.learn.beans.services.BookingServiceImpl;
import edu.learn.beans.services.DiscountService;
import edu.learn.beans.services.DiscountServiceImpl;
import edu.learn.beans.services.EventService;
import edu.learn.beans.services.UserAccountService;
import edu.learn.beans.services.UserService;
import edu.learn.beans.services.discount.BirthdayStrategy;
import edu.learn.beans.services.discount.DiscountStrategy;
import edu.learn.beans.services.discount.TicketsStrategy;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestBookingServiceConfiguration {

	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuditoriumRepository auditoriumRepository;
	@Autowired
	private EventService eventService;
	@Autowired
	private AuditoriumService auditoriumService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserAccountService userAccountService;

	@PostConstruct
	public void init() {
		auditoriumRepository.save(Arrays.asList(testHall1(), testHall2()));

		eventRepository.save(Arrays.asList(testEvent1(), testEvent2()));

		tickets().forEach(ticket ->
			bookingRepository.save(new Booking(ticket.getId(), testUser1(), ticket)));
	}

	@Bean
	public BookingService bookingServiceImpl() {
		return new BookingServiceImpl(eventService, auditoriumService, userService,
			discountService(), bookingRepository, 1, userAccountService, 2, 1.2, 1);
	}

	@Bean
	public DiscountService discountService() {
		return new DiscountServiceImpl(Arrays.asList(birthdayBookingStrategy(), ticketsBookingStrategy()));
	}

	@Bean
	public DiscountStrategy birthdayBookingStrategy() {
		return new BirthdayStrategy(0.15, 0);
	}

	@Bean
	public DiscountStrategy ticketsBookingStrategy() {
		return new TicketsStrategy(bookingRepository, 0.5, 3, 0);
	}

	@Bean
	public Event testEvent1() {
		return new Event(1L, "Test event", edu.learn.beans.models.Rate.HIGH, 124.0,
			java.time.LocalDateTime.of(2016, 2, 6, 14, 45, 0),
			testHall1());
	}

	@Bean
	public Event testEvent2() {
		return new Event(2L, "Test event2", Rate.MID, 500.0, java.time.LocalDateTime.of(2016, 12, 6, 9, 35, 0),
			testHall2());
	}

	@Bean
	public User testUser1() {
		User user = userRepository.save(new User(1L, "dmitriy.vbabichev@gmail.com", "Dmytro Babichev", "pass",
			LocalDate.of(1992, 4, 29)));
		userAccountService.save(new UserAccount(user));
		return user;
	}

	@Bean
	public User testUser2() {
		return new User(2L, "laory@yandex.ru", "Dmytro Babichev", "pass", java.time.LocalDate.of(1992, 4, 29));
	}

	@Bean
	public Ticket testTicket1() {
		return new Ticket(1L, testEvent1(), java.time.LocalDateTime.of(2016, 2, 6, 14, 45, 0), Arrays.asList(3, 4),
			testUser1(), 32D);
	}

	@Bean
	public Ticket testTicket2() {
		return new Ticket(2L, testEvent2(), java.time.LocalDateTime.of(2016, 2, 7, 14, 45, 0), Arrays.asList(1, 2),
			testUser1(), 123D);
	}

	@Bean
	public List<Ticket> tickets() {
		return Arrays.asList(testTicket1(), testTicket2());
	}

	@Bean
	public Auditorium testHall1() {
		return new Auditorium(1L, "Test auditorium", 15, Arrays.asList(1, 2, 3, 4, 5));
	}

	@Bean
	public Auditorium testHall2() {
		return new Auditorium(2L, "Test auditorium 2", 8, Collections.singletonList(1));
	}
}
