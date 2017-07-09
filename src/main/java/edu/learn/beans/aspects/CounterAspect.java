package edu.learn.beans.aspects;

import edu.learn.beans.models.Ticket;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA. User: Dmytro_Babichev Date: 2/11/2016 Time: 10:14 AM
 */
@Aspect
@Component
public class CounterAspect {

	protected static final Map<String, Integer> accessByNameCounter = new HashMap<>();
	protected static final Map<String, Integer> getPriceByNameCounter = new HashMap<>();
	protected static final Map<String, Integer> bookByNameCounter = new HashMap<>();

	public static Map<String, Integer> getAccessByNameStat() {
		return copyMap(accessByNameCounter);
	}

	public static Map<String, Integer> getGetPriceByNameStat() {
		return copyMap(getPriceByNameCounter);
	}

	public static Map<String, Integer> getBookTicketByNameStat() {
		return copyMap(bookByNameCounter);
	}

	private static <K, V> Map<K, V> copyMap(Map<K, V> src) {
		return src.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	@Pointcut("(execution(* edu.learn.beans.services.EventService.getEvent(String, ..)) && args(eventName, ..)) || "
		+ "execution(* edu.learn.beans.services.EventService.getByName(String)) && args(eventName))")
	private void accessedByName(String eventName) {
	}

	@Pointcut("(execution(* edu.learn.beans.services.BookingService.getTicketPrice(String, ..)) && args(eventName,..))")
	private void getPriceByName(String eventName) {
	}

	@Pointcut("(execution(* edu.learn.beans.services.BookingService.bookTicket(*, edu.learn.beans.models.Ticket)) && args(*, ticket))")
	private void bookTicketByName(Ticket ticket) {
	}

	@Before("accessedByName(eventName)")
	public void countAccessByName(String eventName) {
		increaseCounter(accessByNameCounter, eventName);
	}

	@Before("getPriceByName(eventName)")
	public void countGetPriceByName(String eventName) {
		increaseCounter(getPriceByNameCounter, eventName);
	}

	@AfterReturning("bookTicketByName(ticket)")
	public void countBookTicketByName(Ticket ticket) {
		increaseCounter(bookByNameCounter, ticket.getEvent().getName());
	}

	private <K> void increaseCounter(Map<K, Integer> stat, K key) {
		stat.put(key, stat.getOrDefault(key, 0) + 1);
	}

}