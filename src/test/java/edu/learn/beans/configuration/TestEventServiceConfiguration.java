package edu.learn.beans.configuration;

import edu.learn.beans.models.Event;
import edu.learn.beans.models.Rate;
import edu.learn.beans.repository.EventRepository;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA. User: Dmytro_Babichev Date: 13/2/16 Time: 6:10 PM
 */
@Configuration
public class TestEventServiceConfiguration extends TestAuditoriumConfiguration {

	@Autowired
	private EventRepository eventRepository;

	@Bean
//	@Scope("prototype")
	public Event testEvent1() {
		Event event = new Event("Test event", Rate.HIGH, 124.0,
			LocalDateTime.of(2016, 2, 6, 14, 45, 0),
			testHall1());
		return eventRepository.save(event);
	}

	@Bean
//	@Scope("prototype")
	public Event testEvent2() {
		Event event = new Event("Test event2", Rate.MID, 500.0, LocalDateTime.of(2016, 12, 6, 9, 35, 0),
			testHall2());
		return eventRepository.save(event);
	}

	@Bean
//	@Scope("prototype")
	public Event testEvent3() {
		Event event = new Event("Test event", Rate.LOW, 50.0, LocalDateTime.of(2016, 12, 29, 10, 0, 0),
			testHall1());
		return eventRepository.save(event);
	}
}
