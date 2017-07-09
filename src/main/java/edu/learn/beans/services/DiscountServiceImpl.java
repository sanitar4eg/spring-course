package edu.learn.beans.services;

import edu.learn.beans.models.Event;
import edu.learn.beans.models.User;
import edu.learn.beans.services.discount.DiscountStrategy;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA. User: Dmytro_Babichev Date: 2/4/2016 Time: 11:23 AM
 */
@Service
@Transactional
public class DiscountServiceImpl implements DiscountService {

	public static final double MAX_DISCOUNT = 0.8;

	private final List<DiscountStrategy> strategies;

	@Autowired
	public DiscountServiceImpl(@Value("#{strategies}") List<DiscountStrategy> strategies) {
		this.strategies = Collections.unmodifiableList(strategies);
	}

	@Override
	public double getDiscount(User user, Event event) {
		final Double discount = strategies.stream().map(strategy -> strategy.calculateDiscount(user)).reduce(
			(a, b) -> a + b).orElse(0.0);
		return Double.compare(discount, MAX_DISCOUNT) > 0 ? MAX_DISCOUNT : discount;
	}
}