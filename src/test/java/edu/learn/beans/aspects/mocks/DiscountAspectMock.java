package edu.learn.beans.aspects.mocks;

import edu.learn.beans.aspects.DiscountAspect;
import edu.learn.beans.services.discount.BirthdayStrategy;
import edu.learn.beans.services.discount.TicketsStrategy;
import java.util.HashMap;

public class DiscountAspectMock extends DiscountAspect {

	public static void cleanup() {
		discountPerUserCounter.put(BirthdayStrategy.class.getSimpleName(), new HashMap<>());
		discountPerUserCounter.put(TicketsStrategy.class.getSimpleName(), new HashMap<>());
	}
}
