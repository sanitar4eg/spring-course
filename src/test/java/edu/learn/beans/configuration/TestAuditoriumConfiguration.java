package edu.learn.beans.configuration;

import edu.learn.beans.models.Auditorium;
import edu.learn.beans.repository.AuditoriumRepository;
import java.util.Arrays;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestAuditoriumConfiguration {

	@Autowired
	private AuditoriumRepository auditoriumRepository;

	@Bean
	public Auditorium testHall1() {
		Auditorium auditorium = new Auditorium(1L, "Test auditorium", 15, Arrays.asList(1, 2, 3, 4, 5));
		return auditoriumRepository.save(auditorium);
	}

	@Bean
	public Auditorium testHall2() {
		Auditorium auditorium = new Auditorium(2L, "Test auditorium 2", 8, Collections.singletonList(1));
		return auditoriumRepository.save(auditorium);
	}
}
