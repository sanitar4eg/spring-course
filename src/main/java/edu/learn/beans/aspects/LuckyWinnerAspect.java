package edu.learn.beans.aspects;

import edu.learn.beans.models.Ticket;
import edu.learn.beans.models.User;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Aspect
@Component
@PropertySource({"classpath:aspects/aspects.properties"})
public class LuckyWinnerAspect {

	protected static final Set<String> luckyUsers = new HashSet<>();
	private final int luckyPercentage;

	@Autowired
	public LuckyWinnerAspect(@Value("${lucky.percentage}") int luckyPercentage) {
		this.luckyPercentage = luckyPercentage;
	}

	public static List<String> getLuckyUsers() {
		return new ArrayList<>(luckyUsers);
	}

	@Pointcut(value =
		"(execution(* edu.learn.beans.services.BookingService.bookTicket(edu.learn.beans.models.User, edu.learn.beans.models.Ticket)) && args(user, ticket))", argNames = "user,ticket")
	private void bookTicket(User user, Ticket ticket) {
	}

	@Around(value = "bookTicket(user, ticket)", argNames = "joinPoint,user,ticket")
	public Object countBookTicketByName(ProceedingJoinPoint joinPoint, User user, Ticket ticket) throws Throwable {
		final int randomInt = ThreadLocalRandom.current().nextInt(100 - luckyPercentage + 1);
		if (randomInt == 0) {
			Ticket luckyTicket = new Ticket(ticket.getEvent(), ticket.getDateTime(), ticket.getSeatsList(),
				ticket.getUser(), 0.0);
			luckyUsers.add(user.getEmail());
			return joinPoint.proceed(new Object[]{user, luckyTicket});
		} else {
			return joinPoint.proceed();
		}
	}
}
