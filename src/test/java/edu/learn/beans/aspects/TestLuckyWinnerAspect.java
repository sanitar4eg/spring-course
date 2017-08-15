package edu.learn.beans.aspects;

import static org.junit.Assert.assertEquals;

import edu.learn.beans.TestConfiguration;
import edu.learn.beans.aspects.mocks.LuckyWinnerAspectMock;
import edu.learn.beans.configuration.TestAspectsConfiguration;
import edu.learn.beans.models.Ticket;
import edu.learn.beans.models.User;
import edu.learn.beans.repository.AuditoriumRepository;
import edu.learn.beans.repository.BookingRepository;
import edu.learn.beans.repository.EventRepository;
import edu.learn.beans.repository.UserRepository;
import edu.learn.beans.services.BookingService;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA. User: Dmytro_Babichev Date: 13/2/16 Time: 7:20 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfiguration.class, TestAspectsConfiguration.class})
@Transactional
public class TestLuckyWinnerAspect {

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

	@Before
	public void init() {
		LuckyWinnerAspectMock.cleanup();
	}

	@After
	public void cleanup() {
		LuckyWinnerAspectMock.cleanup();
		auditoriumRepository.deleteAll();
		userRepository.deleteAll();
		eventRepository.deleteAll();
		bookingRepository.deleteAll();
	}

	@Test
	public void testCalculateDiscount() {
		User user = (User) applicationContext.getBean("testUser1");
		User discountUser = new User(user.getId(), user.getEmail(), user.getName(), LocalDate.now());
		Ticket ticket1 = (Ticket) applicationContext.getBean("testTicket1");
		bookingService.bookTicket(discountUser,
			new Ticket(ticket1.getEvent(), ticket1.getDateTime(), Arrays.asList(5, 6), user, ticket1.getPrice()));
		bookingService.bookTicket(discountUser,
			new Ticket(ticket1.getEvent(), ticket1.getDateTime(), Arrays.asList(7, 8), user, ticket1.getPrice()));
		bookingService.bookTicket(discountUser,
			new Ticket(ticket1.getEvent(), ticket1.getDateTime(), Arrays.asList(9, 10), user, ticket1.getPrice()));
		bookingService.bookTicket(discountUser,
			new Ticket(ticket1.getEvent(), ticket1.getDateTime(), Arrays.asList(11, 12), user, ticket1.getPrice()));

		assertEquals(Collections.singletonList(user.getEmail()), LuckyWinnerAspectMock.getLuckyUsers());
	}
}
