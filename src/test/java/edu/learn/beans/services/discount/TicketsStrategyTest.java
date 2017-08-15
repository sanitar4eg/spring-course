package edu.learn.beans.services.discount;

import static org.junit.Assert.assertEquals;

import edu.learn.beans.TestConfiguration;
import edu.learn.beans.configuration.TestStrategiesConfiguration;
import edu.learn.beans.models.User;
import edu.learn.beans.repository.UserRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfiguration.class, TestStrategiesConfiguration.class})
public class TicketsStrategyTest {

	private static final Logger LOG = LoggerFactory.getLogger(TicketsStrategyTest.class);

	@Autowired
	@Qualifier("ticketsStrategy")
	private TicketsStrategy strategy;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void testCalculateDiscount_UserHasDiscount() throws Exception {
		LOG.info(strategy.getClass().getName());
		User userWithDiscount =
			new User("test@ema.il", TestStrategiesConfiguration.USER_THAT_BOOKED_TICKET, LocalDate.now());
		userWithDiscount = userRepository.save(userWithDiscount);
		double discount = strategy.calculateDiscount(userWithDiscount);
		assertEquals("User: [" + userWithDiscount + "] has tickets discount", strategy.ticketsDiscountValue, discount,
			0.00001);
	}

	@Test
	public void testCalculateDiscount_UserHasNoDiscount() throws Exception {
		User userWithoutDiscount = new User("test@ema.il", "Test Name 2", LocalDate.now().minus(1, ChronoUnit.DAYS));
		userWithoutDiscount = userRepository.save(userWithoutDiscount);
		double discount = strategy.calculateDiscount(userWithoutDiscount);
		assertEquals("User: [" + userWithoutDiscount + "] doesn't have tickets discount", strategy.defaultDiscount,
			discount, 0.00001);
	}
}
