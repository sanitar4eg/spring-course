package edu.learn.beans.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import edu.learn.beans.TestConfiguration;
import edu.learn.beans.configuration.TestBookingServiceConfiguration;
import edu.learn.beans.models.Event;
import edu.learn.beans.models.Ticket;
import edu.learn.beans.models.User;
import edu.learn.beans.repository.AuditoriumRepository;
import edu.learn.beans.repository.BookingRepository;
import edu.learn.beans.repository.EventRepository;
import edu.learn.beans.repository.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfiguration.class, TestBookingServiceConfiguration.class})
@Transactional
public class BookingServiceImplTest {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private BookingService bookingService;

	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuditoriumRepository auditoriumRepository;

	@After
	public void cleanup() {
		bookingRepository.deleteAll();
		eventRepository.deleteAll();
		auditoriumRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Test
	public void testGetTicketsForEvent() throws Exception {
		Event testEvent1 = (Event) applicationContext.getBean("testEvent1");
		Ticket ticket = (Ticket) applicationContext.getBean("testTicket1");
		List<Ticket> ticketsForEvent = bookingService.getTicketsForEvent(testEvent1.getName(),
			testEvent1.getAuditorium().getName(),
			testEvent1.getDateTime());
		assertEquals("Tickets should match", Collections.singletonList(ticket), ticketsForEvent);
	}

	@Test(expected = RuntimeException.class)
	public void testBookTicket_NotRegistered() throws Exception {
		Event testEvent1 = (Event) applicationContext.getBean("testEvent1");
		List<Ticket> before = bookingService.getTicketsForEvent(testEvent1.getName(),
			testEvent1.getAuditorium().getName(),
			testEvent1.getDateTime());
		User newUser = new User(UUID.randomUUID().toString(), UUID.randomUUID().toString(), LocalDate.now());
		Ticket newTicket = new Ticket(testEvent1, LocalDateTime.now(), Arrays.asList(3, 4), newUser, 0.0);
		bookingService.bookTicket(newUser, newTicket);
	}

	@Test(expected = RuntimeException.class)
	public void testBookTicket_AlreadyBooked() throws Exception {
		Event testEvent1 = (Event) applicationContext.getBean("testEvent1");
		List<Ticket> before = bookingService.getTicketsForEvent(testEvent1.getName(),
			testEvent1.getAuditorium().getName(),
			testEvent1.getDateTime());
		User testUser2 = (User) applicationContext.getBean("testUser2");
		Ticket newTicket = new Ticket(testEvent1, LocalDateTime.now(), Arrays.asList(3, 4), testUser2, 0.0);
		bookingService.bookTicket(testUser2, newTicket);
	}

	@Test
	public void testBookTicket() throws Exception {
		Event testEvent1 = (Event) applicationContext.getBean("testEvent1");
		List<Ticket> before = bookingService.getTicketsForEvent(testEvent1.getName(),
			testEvent1.getAuditorium().getName(),
			testEvent1.getDateTime());
		User testUser1 = (User) applicationContext.getBean("testUser1");
		Ticket newTicket = new Ticket(testEvent1, LocalDateTime.now(), Arrays.asList(5, 6), testUser1, 0.0);
		Ticket bookedTicket = bookingService.bookTicket(testUser1, newTicket);
		List<Ticket> after = bookingService.getTicketsForEvent(testEvent1.getName(),
			testEvent1.getAuditorium().getName(),
			testEvent1.getDateTime());
		before.add(bookedTicket);
		assertTrue("Events should change", after.containsAll(before));
		assertTrue("Events should change", before.containsAll(after));
	}

	@Test
	public void testGetTicketPrice() throws Exception {
		Ticket ticket = (Ticket) applicationContext.getBean("testTicket1");
		Event event = ticket.getEvent();
		double ticketPrice = bookingService.getTicketPrice(event.getName(), event.getAuditorium().getName(),
			event.getDateTime(), ticket.getSeatsList(),
			ticket.getUser());
		assertEquals("Price is wrong", 297.6, ticketPrice, 0.00001);
	}

	@Test
	public void testGetTicketPrice_WithoutDiscount() throws Exception {
		Ticket ticket = (Ticket) applicationContext.getBean("testTicket1");
		User user = userRepository.save(new User("dadsada", "asdasda", LocalDate.now().minus(1, ChronoUnit.DAYS)));
		Event event = ticket.getEvent();
		double ticketPrice = bookingService.getTicketPrice(event.getName(), event.getAuditorium().getName(),
			event.getDateTime(), ticket.getSeatsList(), user);
		assertEquals("Price is wrong", 595.2, ticketPrice, 0.00001);
	}

	@Test
	public void testGetTicketPrice_DiscountsForTicketsAndForBirthday() throws Exception {
		Ticket ticket = (Ticket) applicationContext.getBean("testTicket1");
		User testUser = new User(UUID.randomUUID().toString(), UUID.randomUUID().toString(), LocalDate.now());
		User registeredUser = userRepository.save(testUser);
		bookingService.bookTicket(registeredUser,
			new Ticket(ticket.getEvent(), LocalDateTime.now(), Collections.singletonList(1),
				registeredUser, 0.0));
		bookingService.bookTicket(registeredUser,
			new Ticket(ticket.getEvent(), LocalDateTime.now(), Collections.singletonList(2),
				registeredUser, 0.0));
		Event event = ticket.getEvent();
		double ticketPrice = bookingService.getTicketPrice(event.getName(), event.getAuditorium().getName(),
			event.getDateTime(), Arrays.asList(5, 6, 7, 8),
			registeredUser);
		assertEquals("Price is wrong", 260.4, ticketPrice, 0.00001);
	}

	@Test
	public void testGetTicketPrice_DiscountsForTicketsAndForBirthday_MidRate() throws Exception {
		Ticket ticket = (Ticket) applicationContext.getBean("testTicket2");
		User testUser = new User(UUID.randomUUID().toString(), UUID.randomUUID().toString(), LocalDate.now());
		User registeredUser = userRepository.save(testUser);
		bookingService.bookTicket(registeredUser,
			new Ticket(ticket.getEvent(), LocalDateTime.now(), Collections.singletonList(3),
				registeredUser, 0.0));
		bookingService.bookTicket(registeredUser,
			new Ticket(ticket.getEvent(), LocalDateTime.now(), Collections.singletonList(4),
				registeredUser, 0.0));
		Event event = ticket.getEvent();
		double ticketPrice = bookingService.getTicketPrice(event.getName(), event.getAuditorium().getName(),
			event.getDateTime(), Arrays.asList(5, 6, 7), registeredUser);
		assertEquals("Price is wrong", 525, ticketPrice, 0.00001);
	}
}
