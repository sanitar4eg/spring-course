package edu.learn.beans.services.discount;

import static org.junit.Assert.assertEquals;

import edu.learn.beans.models.User;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA. User: Dmytro_Babichev Date: 06/2/16 Time: 2:16 AM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BirthdayStrategy.class)
public class BirthdayStrategyTest {

	@Autowired
	private BirthdayStrategy strategy;

	@Test
	public void testCalculateDiscount_UserHasDiscount() throws Exception {
		User userWithDiscount = new User("test@ema.il", "Test Name", LocalDate.now());
		double discount = strategy.calculateDiscount(userWithDiscount);
		assertEquals("User: [" + userWithDiscount + "] has birthday discount", strategy.birthdayDiscountValue, discount,
			0.00001);
	}

	@Test
	public void testCalculateDiscount_UserHasNoDiscount() throws Exception {
		User userWithoutDiscount = new User("test@ema.il", "Test Name", LocalDate.now().minus(1, ChronoUnit.DAYS));
		double discount = strategy.calculateDiscount(userWithoutDiscount);
		assertEquals("User: [" + userWithoutDiscount + "] doesn't have birthday discount",
			strategy.defaultDiscountValue, discount, 0.00001);
	}
}
