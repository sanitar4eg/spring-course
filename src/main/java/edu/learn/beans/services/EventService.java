package edu.learn.beans.services;

import edu.learn.beans.models.Auditorium;
import edu.learn.beans.models.Event;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

	Event create(Event event);

	void remove(Event event);

	Event getEvent(String name, Auditorium auditorium, LocalDateTime dateTime);

	List<Event> getByName(String name);

	Event getById(Long id);

	List<Event> getAll();

	List<Event> getForDateRange(LocalDateTime from, LocalDateTime to);

	List<Event> getNextEvents(LocalDateTime to);

	Event assignAuditorium(Event event, Auditorium auditorium, LocalDateTime date);
}
