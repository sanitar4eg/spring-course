package edu.learn.beans.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.learn.util.LocalDateTimeDeserializer;
import edu.learn.util.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Created with IntelliJ IDEA. User: Dmytro_Babichev Date: 2/1/2016 Time: 7:42 PM
 */
@Entity
public class Event {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	@Enumerated
	private Rate rate;
	private double basePrice;
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime dateTime;
	@ManyToOne(fetch = FetchType.EAGER)
	private Auditorium auditorium;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, mappedBy = "event")
	@JsonIgnore
	private List<Ticket> tickets;

	public Event() {
	}

	public Event(String name, Rate rate, double basePrice, LocalDateTime dateTime, Auditorium auditorium) {
		this(null, name, rate, basePrice, dateTime, auditorium);
	}

	public Event(Long id, String name, Rate rate, double basePrice, LocalDateTime dateTime, Auditorium auditorium) {
		this.id = id;
		this.name = name;
		this.rate = rate;
		this.basePrice = basePrice;
		this.dateTime = dateTime;
		this.auditorium = auditorium;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Rate getRate() {
		return rate;
	}

	public void setRate(Rate rate) {
		this.rate = rate;
	}

	public double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public Auditorium getAuditorium() {
		return auditorium;
	}

	public void setAuditorium(Auditorium auditorium) {
		this.auditorium = auditorium;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Event)) {
			return false;
		}

		Event event = (Event) o;

		if (!Objects.equals(id, event.id)) {
			return false;
		}
		if (Double.compare(event.basePrice, basePrice) != 0) {
			return false;
		}
		if (name != null ? !name.equals(event.name) : event.name != null) {
			return false;
		}
		if (rate != event.rate) {
			return false;
		}
		if (dateTime != null ? !dateTime.equals(event.dateTime) : event.dateTime != null) {
			return false;
		}
		return auditorium != null ? auditorium.equals(event.auditorium) : event.auditorium == null;

	}

	@Override
	public int hashCode() {
		long id = Optional.ofNullable(getId()).orElse(-1L);
		int result;
		long temp;
		result = (int) (id ^ (id >>> 32));
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (rate != null ? rate.hashCode() : 0);
		temp = Double.doubleToLongBits(basePrice);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
		result = 31 * result + (auditorium != null ? auditorium.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Event{" +
			"id=" + id +
			", name='" + name + '\'' +
			", rate=" + rate +
			", basePrice=" + basePrice +
			", dateTime=" + dateTime +
			", auditorium=" + auditorium +
			'}';
	}
}
