package edu.learn.beans.services;

import edu.learn.beans.models.Auditorium;
import edu.learn.beans.repository.AuditoriumRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuditoriumServiceImpl implements AuditoriumService {

	private final AuditoriumRepository auditoriumRepository;

	@Autowired
	public AuditoriumServiceImpl(AuditoriumRepository auditoriumRepository) {
		this.auditoriumRepository = auditoriumRepository;
	}

	@Override
	public List<Auditorium> getAuditoriums() {
		return auditoriumRepository.findAll();
	}

	@Override
	public Auditorium getByName(String name) {
		return auditoriumRepository.getByName(name);
	}

	@Override
	public int getSeatsNumber(String auditoriumName) {
		return auditoriumRepository.getByName(auditoriumName).getSeatsNumber();
	}

	@Override
	public List<Integer> getVipSeats(String auditoriumName) {
		return auditoriumRepository.getByName(auditoriumName).getVipSeatsList();
	}
}
