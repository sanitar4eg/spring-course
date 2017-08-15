package edu.learn.beans.services;

import edu.learn.beans.models.Ticket;
import edu.learn.beans.models.User;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {

	double getTicketPrice(String event, String auditorium, LocalDateTime dateTime, List<Integer> seats, User user);

	Ticket bookTicket(User user, Ticket ticket);

	List<Ticket> getTicketsForEvent(String event, String auditorium, LocalDateTime date);
}
