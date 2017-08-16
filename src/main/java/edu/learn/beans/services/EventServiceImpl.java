package edu.learn.beans.services;

import edu.learn.beans.models.Auditorium;
import edu.learn.beans.models.Event;
import edu.learn.beans.repository.EventRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("eventServiceImpl")
@Transactional
public class EventServiceImpl implements EventService {

	private final EventRepository eventRepository;

	@Autowired
	public EventServiceImpl(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	public Event create(Event event) {
		Event existed = eventRepository
			.findByAuditoriumAndDateTime(event.getAuditorium(), event.getDateTime());
		if (Objects.nonNull(existed)) {
			throw new IllegalStateException(String.format(
				"Unable to store event: [%s]. Event with such auditorium: [%s] on date: [%s] is already created.",
				event, event.getAuditorium(), event.getDateTime()));
		}
		return eventRepository.save(event);
	}

	@Transactional
	public void remove(Event event) {
		eventRepository.delete(event);
		eventRepository.flush();
	}

	public List<Event> getByName(String name) {
		return eventRepository.getByName(name);
	}

	@Override
	public Event getById(Long id) {
		return eventRepository.getOne(id);
	}

	public Event getEvent(String name, Auditorium auditorium, LocalDateTime dateTime) {
		return eventRepository.getByNameAndAuditoriumAndDateTime(name, auditorium, dateTime);
	}

	public List<Event> getAll() {
		return eventRepository.findAll();
	}

	public List<Event> getForDateRange(LocalDateTime from, LocalDateTime to) {
		return eventRepository.getByDateTimeBetween(from, to);
	}

	public List<Event> getNextEvents(LocalDateTime to) {
		return eventRepository.getByDateTimeAfter(to);
	}

	public Event assignAuditorium(Event event, Auditorium auditorium, LocalDateTime date) {
		final Event updatedEvent = new Event(event.getId(), event.getName(), event.getRate(), event.getBasePrice(),
			date, auditorium);
		return eventRepository.save(updatedEvent);
	}
}
