package edu.learn.beans.configuration;

import edu.learn.beans.models.User;
import edu.learn.beans.repository.BookingRepository;
import edu.learn.beans.repository.UserRepository;
import edu.learn.beans.services.DiscountService;
import edu.learn.beans.services.DiscountServiceImpl;
import edu.learn.beans.services.discount.BirthdayStrategy;
import edu.learn.beans.services.discount.TicketsStrategy;
import java.time.LocalDate;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestStrategiesConfiguration {

	public static final String USER_THAT_BOOKED_TICKET = "Test User";

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookingRepository bookingRepository;

	@PostConstruct
	public void init() {
		userRepository.save(new User(USER_THAT_BOOKED_TICKET, USER_THAT_BOOKED_TICKET, LocalDate.now()));
	}

	@Bean
	public BirthdayStrategy birthdayStrategy() {
		return new BirthdayStrategy(1.0, 0);
	}

	@Bean
	public TicketsStrategy ticketsStrategy() {
		return new TicketsStrategy(bookingRepository, 0.5, 2, 0);
	}

	@Bean
	public DiscountService discountServiceImpl() {
		return new DiscountServiceImpl(Arrays.asList(birthdayStrategy(), ticketsStrategy()));
	}
}
