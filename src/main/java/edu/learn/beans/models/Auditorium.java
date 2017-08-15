package edu.learn.beans.models;

import edu.learn.util.CsvUtil;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created with IntelliJ IDEA. User: Dmytro_Babichev Date: 2/1/2016 Time: 7:55 PM
 */
@Entity
public class Auditorium {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private int seatsNumber;
	private String vipSeats;

	public Auditorium() {
	}

	public Auditorium(String name, int seatsNumber, List<Integer> vipSeats) {
		this(null, name, seatsNumber, vipSeats);
	}

	public Auditorium(Long id, String name, int seatsNumber, List<Integer> vipSeats) {
		this(id, name, seatsNumber, CsvUtil.fromListToCsv(vipSeats));
	}

	public Auditorium(Long id, String name, int seatsNumber, String vipSeats) {
		this.id = id;
		this.name = name;
		this.seatsNumber = seatsNumber;
		this.vipSeats = vipSeats;
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

	public int getSeatsNumber() {
		return seatsNumber;
	}

	public void setSeatsNumber(int seatsNumber) {
		this.seatsNumber = seatsNumber;
	}

	public String getVipSeats() {
		return vipSeats;
	}

	public void setVipSeats(String vipSeats) {
		this.vipSeats = vipSeats;
	}

	public List<Integer> getVipSeatsList() {
		return CsvUtil.fromCsvToList(vipSeats, Integer::valueOf);
	}

	public void setVipSeatsList(List<Integer> vipSeats) {
		this.vipSeats = CsvUtil.fromListToCsv(vipSeats);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Auditorium)) {
			return false;
		}

		Auditorium that = (Auditorium) o;

		if (!Objects.equals(id, that.id)) {
			return false;
		}
		if (seatsNumber != that.seatsNumber) {
			return false;
		}
		if (name != null ? !name.equals(that.name) : that.name != null) {
			return false;
		}
		return vipSeats != null ? vipSeats.equals(that.vipSeats) : that.vipSeats == null;

	}

	@Override
	public int hashCode() {
		long id = Optional.ofNullable(getId()).orElse(-1L);
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + seatsNumber;
		result = 31 * result + (vipSeats != null ? vipSeats.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Auditorium{" +
			"id=" + id +
			", name='" + name + '\'' +
			", seatsNumber=" + seatsNumber +
			", vipSeats=" + vipSeats +
			'}';
	}
}
