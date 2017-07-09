package edu.learn.beans.services;

import edu.learn.beans.models.Auditorium;
import edu.learn.beans.models.Event;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: Dmytro_Babichev Date: 2/3/2016 Time: 11:02 AM
 */
public interface EventService {

	Event create(Event event);

	void remove(Event event);

	Event getEvent(String name, Auditorium auditorium, LocalDateTime dateTime);

	List<Event> getByName(String name);

	List<Event> getAll();

	List<Event> getForDateRange(LocalDateTime from, LocalDateTime to);

	List<Event> getNextEvents(LocalDateTime to);

	Event assignAuditorium(Event event, Auditorium auditorium, LocalDateTime date);
}
