package edu.learn.beans.services;

import edu.learn.beans.models.Auditorium;
import java.util.List;

public interface AuditoriumService {

	List<Auditorium> getAuditoriums();

	Auditorium getByName(String name);

	int getSeatsNumber(String auditoriumName);

	List<Integer> getVipSeats(String auditoriumName);
}
