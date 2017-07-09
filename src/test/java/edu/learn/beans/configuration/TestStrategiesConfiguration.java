package edu.learn.beans.configuration;

import edu.learn.beans.daos.BookingDAO;
import edu.learn.beans.daos.mocks.BookingDAODiscountMock;
import edu.learn.beans.services.DiscountService;
import edu.learn.beans.services.DiscountServiceImpl;
import edu.learn.beans.services.discount.BirthdayStrategy;
import edu.learn.beans.services.discount.TicketsStrategy;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA. User: Dmytro_Babichev Date: 13/2/16 Time: 3:36 PM
 */
@Configuration
public class TestStrategiesConfiguration {

	@Bean
	public BirthdayStrategy birthdayStrategy() {
		return new BirthdayStrategy(1.0, 0);
	}

	@Bean
	public TicketsStrategy ticketsStrategy() {
		return new TicketsStrategy(bookingDiscountDAO(), 0.5, 2, 0);
	}

	@Bean
	public BookingDAO bookingDiscountDAO() {
		return new BookingDAODiscountMock("Test User", 1);
	}

	@Bean
	public DiscountService discountServiceImpl() {
		return new DiscountServiceImpl(Arrays.asList(birthdayStrategy(), ticketsStrategy()));
	}
}
