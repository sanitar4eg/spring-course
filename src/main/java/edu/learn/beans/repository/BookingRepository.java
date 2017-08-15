package edu.learn.beans.repository;

import edu.learn.beans.models.Booking;
import edu.learn.beans.models.Event;
import edu.learn.beans.models.Ticket;
import edu.learn.beans.models.User;
import java.util.List;
import java.util.Objects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA. User: Dmytro_Babichev Date: 2/4/2016 Time: 10:21 AM
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

	static void validateUser(User user) {
		if (Objects.isNull(user)) {
			throw new NullPointerException("User is [null]");
		}
		if (Objects.isNull(user.getEmail())) {
			throw new NullPointerException("User email is [null]");
		}
	}

	static void validateTicket(Ticket ticket) {
		if (Objects.isNull(ticket)) {
			throw new NullPointerException("Ticket is [null]");
		}
	}

	List<Booking> getAllByTicketEvent(Event event);

	Long countByUser(User user);
}
