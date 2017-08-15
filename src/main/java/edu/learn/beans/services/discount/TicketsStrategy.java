package edu.learn.beans.services.discount;

import edu.learn.beans.models.User;
import edu.learn.beans.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("ticketsStrategy")
@PropertySource({"classpath:strategies/strategies.properties"})
@Transactional
public class TicketsStrategy implements DiscountStrategy {

	final double ticketsDiscountValue;
	final double defaultDiscount;
	private final BookingRepository bookingRepository;
	private final int discountThreshold;

	@Autowired
	public TicketsStrategy(BookingRepository bookingRepository,
		@Value("${tickets.discount}") double ticketsDiscountValue,
		@Value("${tickets.discount.threshold}") int discountThreshold,
		@Value("${tickets.discount.default}") double defaultDiscount) {
		this.bookingRepository = bookingRepository;
		this.ticketsDiscountValue = ticketsDiscountValue;
		this.discountThreshold = discountThreshold;
		this.defaultDiscount = defaultDiscount;
	}

	@Override
	public double calculateDiscount(User user) {
		final long boughtTicketsCount = bookingRepository.countByUser(user);
		if ((boughtTicketsCount + 1) % discountThreshold == 0) {
			return ticketsDiscountValue;
		}
		return defaultDiscount;
	}
}
