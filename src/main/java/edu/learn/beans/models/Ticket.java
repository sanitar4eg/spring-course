package edu.learn.beans.models;

import edu.learn.util.CsvUtil;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Ticket {

	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "event_id")
	private Event event;
	private LocalDateTime dateTime;
	private String seats;
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	private Double price;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "ticket")
	private List<Booking> bookings;

	public Ticket() {
	}

	public Ticket(Event event, LocalDateTime dateTime, List<Integer> seats, User user, double price) {
		this(null, event, dateTime, seats, user, price);
	}

	public Ticket(Long id, Event event, LocalDateTime dateTime, List<Integer> seats, User user, Double price) {
		this(id, event, dateTime, CsvUtil.fromListToCsv(seats), user, price);
	}

	public Ticket(Long id, Event event, LocalDateTime dateTime, String seats, User user, Double price) {
		this.id = id;
		this.event = event;
		this.dateTime = dateTime;
		this.user = user;
		this.price = price;
		this.seats = seats;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public String getSeats() {
		return seats;
	}

	public void setSeats(String seats) {
		this.seats = seats;
	}

	public List<Integer> getSeatsList() {
		return CsvUtil.fromCsvToList(seats, Integer::valueOf);
	}

	public void setSeatsList(List<Integer> seats) {
		this.seats = CsvUtil.fromListToCsv(seats);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Ticket)) {
			return false;
		}

		Ticket ticket = (Ticket) o;

		if (event != null ? !event.equals(ticket.event) : ticket.event != null) {
			return false;
		}
		if (dateTime != null ? !dateTime.equals(ticket.dateTime) : ticket.dateTime != null) {
			return false;
		}
		if (seats != null ? !seats.equals(ticket.seats) : ticket.seats != null) {
			return false;
		}
		if (user != null ? !user.equals(ticket.user) : ticket.user != null) {
			return false;
		}
		return price != null ? price.equals(ticket.price) : ticket.price == null;

	}

	@Override
	public int hashCode() {
		int result = event != null ? event.hashCode() : 0;
		result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
		result = 31 * result + (seats != null ? seats.hashCode() : 0);
		result = 31 * result + (user != null ? user.hashCode() : 0);
		result = 31 * result + (price != null ? price.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Ticket{" +
			"id=" + id +
			", event=" + event +
			", dateTime=" + dateTime +
			", seats=" + seats +
			", user=" + user +
			", price=" + price +
			'}';
	}

	public Ticket withId(Long ticketId) {
		return new Ticket(ticketId, event, dateTime, seats, user, price);
	}
}
