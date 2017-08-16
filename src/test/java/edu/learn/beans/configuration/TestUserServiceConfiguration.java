package edu.learn.beans.configuration;

import edu.learn.beans.models.User;
import edu.learn.beans.repository.UserRepository;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestUserServiceConfiguration {

	@Autowired
	private UserRepository userRepository;

	@PostConstruct
	public void init() {
		userRepository.save(Arrays.asList(testUser1(), testUser2()));
	}

	@Bean
	public User testUser1() {
		return new User(1L, "dmitriy.vbabichev@gmail.com", "Dmytro Babichev", "pass",
			java.time.LocalDate.of(1992, 4, 29));
	}

	@Bean
	public User testUser2() {
		return new User(2L, "laory@yandex.ru", "Dmytro Babichev", "pass", java.time.LocalDate.of(1992, 4, 29));
	}
}
