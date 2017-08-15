package edu.learn.beans.configuration;

import edu.learn.beans.models.User;
import edu.learn.beans.repository.UserRepository;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA. User: Dmytro_Babichev Date: 2/12/2016 Time: 1:36 PM
 */
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
		return new User(1L, "dmitriy.vbabichev@gmail.com", "Dmytro Babichev", java.time.LocalDate.of(1992, 4, 29));
	}

	@Bean
	public User testUser2() {
		return new User(2L, "laory@yandex.ru", "Dmytro Babichev", java.time.LocalDate.of(1992, 4, 29));
	}
}
