package edu.learn.beans.repository;

import edu.learn.beans.models.Auditorium;
import edu.learn.beans.models.Event;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA. User: Dmytro_Babichev Date: 2/2/2016 Time: 12:35 PM
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

	static void validateEvent(Event event) {
		if (Objects.isNull(event)) {
			throw new NullPointerException("Event is [null]");
		}
		if (Objects.isNull(event.getName())) {
			throw new NullPointerException("Event's name is [null]. Event: [" + event + "]");
		}
		if (Objects.isNull(event.getRate())) {
			throw new NullPointerException("Events's rate is [null]. Event: [" + event + "]");
		}
	}

	Event getByNameAndAuditoriumAndDateTime(String eventName, Auditorium auditorium, LocalDateTime dateTime);

	Event findByAuditoriumAndDateTime(Auditorium auditorium, LocalDateTime dateTime);

	List<Event> getByName(String name);

	List<Event> getByDateTimeBetween(LocalDateTime start, LocalDateTime end);

	List<Event> getByDateTimeAfter(LocalDateTime to);

}
