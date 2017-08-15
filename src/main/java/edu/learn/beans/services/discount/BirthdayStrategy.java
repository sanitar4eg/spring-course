package edu.learn.beans.services.discount;

import edu.learn.beans.models.User;
import java.time.LocalDate;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component("birthdayStrategy")
@PropertySource({"classpath:strategies/strategies.properties"})
public class BirthdayStrategy implements DiscountStrategy {

	public final double birthdayDiscountValue;
	public final double defaultDiscountValue;

	@Autowired
	public BirthdayStrategy(@Value("${birthday.discount}") double birthdayDiscountValue,
		@Value("${birthday.discount.default}") double defaultDiscountValue) {
		this.birthdayDiscountValue = birthdayDiscountValue;
		this.defaultDiscountValue = defaultDiscountValue;
	}

	@Override
	public double calculateDiscount(User user) {
		final LocalDate now = LocalDate.now();
		if (Objects.nonNull(user.getBirthday()) && user.getBirthday().getMonthValue() == now.getMonthValue() &&
			user.getBirthday().getDayOfMonth() == now.getDayOfMonth()) {
			return birthdayDiscountValue;
		}
		return defaultDiscountValue;
	}
}
