package edu.learn.beans.models;

import java.util.Objects;
import java.util.Optional;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Booking {

	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Ticket ticket;

	public Booking() {
	}

	public Booking(User user, Ticket ticket) {
		this(null, user, ticket);
	}

	public Booking(Long id, User user, Ticket ticket) {
		this.id = id;
		this.user = user;
		this.ticket = ticket;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Booking)) {
			return false;
		}

		Booking booking = (Booking) o;

		if (!Objects.equals(id, booking.id)) {
			return false;
		}
		if (user != null ? !user.equals(booking.user) : booking.user != null) {
			return false;
		}
		return ticket != null ? ticket.equals(booking.ticket) : booking.ticket == null;

	}

	@Override
	public int hashCode() {
		long id = Optional.ofNullable(getId()).orElse(-1L);
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (user != null ? user.hashCode() : 0);
		result = 31 * result + (ticket != null ? ticket.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Booking{" +
			"id=" + id +
			", user=" + user +
			", ticket=" + ticket +
			'}';
	}
}
