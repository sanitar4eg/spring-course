package edu.learn.beans.aspects;

import static org.junit.Assert.assertEquals;

import edu.learn.beans.TestConfiguration;
import edu.learn.beans.aspects.mocks.CountAspectMock;
import edu.learn.beans.configuration.TestAspectsConfiguration;
import edu.learn.beans.models.Event;
import edu.learn.beans.models.Ticket;
import edu.learn.beans.models.User;
import edu.learn.beans.repository.AuditoriumRepository;
import edu.learn.beans.repository.BookingRepository;
import edu.learn.beans.repository.EventRepository;
import edu.learn.beans.repository.UserRepository;
import edu.learn.beans.services.BookingService;
import edu.learn.beans.services.EventService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfiguration.class, TestAspectsConfiguration.class})
@Transactional
public class TestCounterAspect {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private BookingService bookingService;

	@Autowired
	private EventService eventService;

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CounterAspect counterAspect;

	@Autowired
	private AuditoriumRepository auditoriumRepository;

	@Before
	public void init() {
		CountAspectMock.cleanup();
	}

	@After
	public void cleanup() {
		CountAspectMock.cleanup();
		auditoriumRepository.deleteAll();
		userRepository.deleteAll();
		eventRepository.deleteAll();
		bookingRepository.deleteAll();
	}

	@Test
	public void testAccessedByName() {
		Event testEvent1 = (Event) applicationContext.getBean("testEvent1");
		eventService.getByName("testName1");
		eventService.getByName("testName2");
		eventService.getByName("testName2");
		eventService.getByName("testName3");
		eventService.getByName(testEvent1.getName());
		eventService.getEvent(testEvent1.getName(), testEvent1.getAuditorium(), testEvent1.getDateTime());
		eventService.getEvent(testEvent1.getName(), testEvent1.getAuditorium(), testEvent1.getDateTime());
		eventService.getByName(testEvent1.getName());
		HashMap<String, Integer> expected = new HashMap<String, Integer>() {{
			put("testName1", 1);
			put("testName2", 2);
			put("testName3", 1);
			put(testEvent1.getName(), 4);
		}};
		assertEquals(expected, CounterAspect.getAccessByNameStat());
	}

	@Test
	public void testGetPriceByName() {
		Event event = (Event) applicationContext.getBean("testEvent1");
		User user = (User) applicationContext.getBean("testUser1");
		List<Integer> seats = Arrays.asList(1, 2, 3, 4);
		bookingService.getTicketPrice(event.getName(), event.getAuditorium().getName(), event.getDateTime(), seats,
			user);
		bookingService.getTicketPrice(event.getName(), event.getAuditorium().getName(), event.getDateTime(), seats,
			user);
		HashMap<String, Integer> expected = new HashMap<String, Integer>() {{
			put(event.getName(), 2);
		}};
		assertEquals(expected, CounterAspect.getGetPriceByNameStat());
	}

	@Test
	public void testBookTicketByName() {
		User user = (User) applicationContext.getBean("testUser1");
		Ticket ticket1 = (Ticket) applicationContext.getBean("testTicket1");
		Ticket ticket2 = (Ticket) applicationContext.getBean("testTicket2");
		bookingService.bookTicket(user, new Ticket(ticket1.getEvent(), ticket1.getDateTime(), Arrays.asList(5, 6), user,
			ticket1.getPrice()));
		bookingService.bookTicket(user, new Ticket(ticket1.getEvent(), ticket1.getDateTime(), Arrays.asList(7, 8), user,
			ticket1.getPrice()));
		bookingService.bookTicket(user, new Ticket(ticket2.getEvent(), ticket2.getDateTime(), Arrays.asList(7, 8), user,
			ticket2.getPrice()));
		HashMap<String, Integer> expected = new HashMap<String, Integer>() {{
			put(ticket1.getEvent().getName(), 2);
			put(ticket2.getEvent().getName(), 1);
		}};
		assertEquals(expected, CounterAspect.getBookTicketByNameStat());
	}
}
