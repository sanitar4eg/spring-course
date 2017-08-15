package edu.learn.beans.configuration;

import edu.learn.beans.services.discount.DiscountStrategy;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StrategiesConfiguration {

	private final DiscountStrategy birthdayStrategy;

	private final DiscountStrategy ticketsStrategy;

	@Autowired
	public StrategiesConfiguration(DiscountStrategy birthdayStrategy, DiscountStrategy ticketsStrategy) {
		this.birthdayStrategy = birthdayStrategy;
		this.ticketsStrategy = ticketsStrategy;
	}

	@Bean(name = "strategies")
	public List<DiscountStrategy> strategies() {
		return Arrays.asList(birthdayStrategy, ticketsStrategy);
	}
}
