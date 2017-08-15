package edu.learn.beans.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import edu.learn.beans.TestConfiguration;
import edu.learn.beans.configuration.TestAuditoriumConfiguration;
import edu.learn.beans.configuration.TestEventServiceConfiguration;
import edu.learn.beans.models.Auditorium;
import edu.learn.beans.models.Event;
import edu.learn.beans.models.Rate;
import edu.learn.beans.repository.BookingRepository;
import edu.learn.beans.repository.EventRepository;
import edu.learn.beans.repository.TicketRepository;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA. User: Dmytro_Babichev Date: 06/2/16 Time: 1:23 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfiguration.class,
	TestAuditoriumConfiguration.class, TestEventServiceConfiguration.class})
@Transactional
public class EventServiceImplTest {

	private static final Logger LOG = LoggerFactory.getLogger(EventServiceImplTest.class);

	private final Event testEvent =
		new Event(UUID.randomUUID().toString(), Rate.HIGH, 1321, LocalDateTime.now(), null);
	@Autowired
	@Value("#{testHall1}")
	Auditorium auditorium;
	@Autowired
	@Qualifier("testHall2")
	Auditorium auditorium2;
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private EventService eventService;
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private TicketRepository ticketRepository;

	@Before
	public void init() {
		LOG.info("!!!");
		testEvent.setAuditorium(auditorium);
		LOG.info("$$$");
	}

	@After
	public void clean() {
		LOG.info("***");
//		bookingRepository.deleteAll();
//		ticketRepository.deleteAll();
		eventRepository.deleteAll();
		LOG.info("###");
	}

	@Test
	public void testCreate() throws Exception {
		List<Event> before = eventService.getAll();
		eventService.create(testEvent);
		List<Event> after = eventService.getAll();
		before.add(testEvent);
		assertTrue("Events should change", after.containsAll(before));
		assertTrue("Events should change", before.containsAll(after));
	}

	@Test
	public void testRemove() throws Exception {
		List<Event> before = eventService.getAll();
		Event eventMock = (Event) applicationContext.getBean("testEvent1");
		Event event = getEvent(eventMock);
		eventService.remove(event);
		List<Event> after = eventService.getAll();
		before.remove(event);
		assertEquals("Events should change", after, before);
	}

	@Test(expected = RuntimeException.class)
	public void testCreate_Exception() throws Exception {
		Event addedEvent = (Event) applicationContext.getBean("testEvent1");
		eventService.create(addedEvent);
	}

	@Test
	public void testGetAll() throws Exception {
		List<Event> all = eventService.getAll();
		Event event1 = (Event) applicationContext.getBean("testEvent1");
		Event event2 = (Event) applicationContext.getBean("testEvent2");
		Event event3 = (Event) applicationContext.getBean("testEvent3");
		List<Event> expected = Arrays.asList(getEvent(event1), getEvent(event2), getEvent(event3));
		LOG.info(all.toString());
		assertTrue("List of events should match", expected.containsAll(all));
		assertTrue("List of events should match", all.containsAll(expected));
	}

	@Test
	public void testGetByName() throws Exception {
		Event eventMock = (Event) applicationContext.getBean("testEvent1");
		Event event1 = getEvent(eventMock);
		Event eventMock3 = (Event) applicationContext.getBean("testEvent3");
		Event event3 = getEvent(eventMock3);
		List<Event> foundByName = eventService.getByName(event1.getName());
		List<Event> expected = Arrays.asList(event1, event3);
		assertTrue("List of events should match", expected.containsAll(foundByName));
		assertTrue("List of events should match", foundByName.containsAll(expected));
	}

	private Event getEvent(Event eventMock) {
		return eventService.getEvent(eventMock.getName(), eventMock.getAuditorium(), eventMock.getDateTime());
	}

	@Test
	public void testGetEvent() throws Exception {
		Event event1 = (Event) applicationContext.getBean("testEvent1");
		Event foundEvent = getEvent(event1);
		assertEquals("Events should match", event1.getAuditorium(), foundEvent.getAuditorium());
		assertEquals("Events should match", event1.getBasePrice(), foundEvent.getBasePrice(), 0.001);
		assertEquals("Events should match", event1.getDateTime(), foundEvent.getDateTime());
		assertEquals("Events should match", event1.getRate(), foundEvent.getRate());
		assertEquals("Events should match", event1.getName(), foundEvent.getName());
	}

	@Test(expected = RuntimeException.class)
	public void testGetEvent_Exception() throws Exception {
		Auditorium auditorium = new Auditorium(UUID.randomUUID().toString(), 1231, Collections.emptyList());
		Event event = eventService.getEvent(UUID.randomUUID().toString(), auditorium, LocalDateTime.now());
		assertNull("There shouldn't be such event in db", event);
	}

	@Test
	public void testAssignAuditorium_createNew() throws Exception {
		LOG.info("auditorium = " + auditorium);
		LOG.info("auditorium2 = " + auditorium2);
		List<Event> before = eventService.getAll();
		Event event = eventService.create(testEvent);
		LOG.info("event = " + event);
		eventService.assignAuditorium(event, auditorium2, event.getDateTime());
		List<Event> after = eventService.getAll();
		before.add(testEvent);
		assertTrue("Events should match", before.containsAll(after));
		assertTrue("Events should match", after.containsAll(before));
	}

	@Test
	public void testAssignAuditorium_update() throws Exception {
		List<Event> before = eventService.getAll();
		Event eventMock = (Event) applicationContext.getBean("testEvent1");
		Event event = getEvent(eventMock);
		eventService.assignAuditorium(event, testEvent.getAuditorium(), testEvent.getDateTime());
		List<Event> after = eventService.getAll();
		before.remove(event);
		before.add(new Event(event.getId(), event.getName(), event.getRate(), event.getBasePrice(),
			testEvent.getDateTime(), testEvent.getAuditorium()));
		LOG.info("before = " + before);
		LOG.info("after = " + after);
		assertTrue("Events should match", before.containsAll(after));
		assertTrue("Events should match", after.containsAll(before));
	}

	@Test
	public void testAssignAuditorium_Exception() throws Exception {
		Event event1 = (Event) applicationContext.getBean("testEvent1");
		Event event2 = (Event) applicationContext.getBean("testEvent2");
		eventService.assignAuditorium(event1, event2.getAuditorium(), event2.getDateTime());
	}
}
