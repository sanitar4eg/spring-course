package edu.learn.beans.services;

import edu.learn.beans.models.Auditorium;
import edu.learn.beans.models.Booking;
import edu.learn.beans.models.Event;
import edu.learn.beans.models.Rate;
import edu.learn.beans.models.Ticket;
import edu.learn.beans.models.User;
import edu.learn.beans.repository.BookingRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("bookingServiceImpl")
@PropertySource({"classpath:strategies/booking.properties"})
@Transactional
public class BookingServiceImpl implements BookingService {

	public static final Logger LOG = LoggerFactory.getLogger(BookingServiceImpl.class);

	private final int minSeatNumber;
	private final double vipSeatPriceMultiplier;
	private final double highRatedPriceMultiplier;
	private final double defaultRateMultiplier;
	private final EventService eventService;
	private final AuditoriumService auditoriumService;
	private final UserService userService;
	private final BookingRepository bookingRepository;
	private final DiscountService discountService;

	@Autowired
	public BookingServiceImpl(EventService eventService, AuditoriumService auditoriumService,
		UserService userService, DiscountService discountService,
		BookingRepository bookingRepository,
		@Value("${min.seat.number}") int minSeatNumber,
		@Value("${vip.seat.price.multiplier}") double vipSeatPriceMultiplier,
		@Value("${high.rate.price.multiplier}") double highRatedPriceMultiplier,
		@Value("${def.rate.price.multiplier}") double defaultRateMultiplier) {
		this.eventService = eventService;
		this.auditoriumService = auditoriumService;
		this.userService = userService;
		this.bookingRepository = bookingRepository;
		this.discountService = discountService;
		this.minSeatNumber = minSeatNumber;
		this.vipSeatPriceMultiplier = vipSeatPriceMultiplier;
		this.highRatedPriceMultiplier = highRatedPriceMultiplier;
		this.defaultRateMultiplier = defaultRateMultiplier;
	}

	@Override
	public double getTicketPrice(String eventName, String auditoriumName, LocalDateTime dateTime, List<Integer> seats,
		User user) {
		if (Objects.isNull(eventName)) {
			throw new NullPointerException("Event name is [null]");
		}
		if (Objects.isNull(seats)) {
			throw new NullPointerException("Seats are [null]");
		}
		if (Objects.isNull(user)) {
			throw new NullPointerException("User is [null]");
		}
		if (seats.contains(null)) {
			throw new NullPointerException("Seats contain [null]");
		}

		final Auditorium auditorium = auditoriumService.getByName(auditoriumName);

		final Event event = eventService.getEvent(eventName, auditorium, dateTime);
		if (Objects.isNull(event)) {
			throw new IllegalStateException(
				"There is no event with name: [" + eventName + "] in auditorium: [" + auditorium + "] on date: ["
					+ dateTime + "]");
		}

		final double baseSeatPrice = event.getBasePrice();
		final double rateMultiplier = event.getRate() == Rate.HIGH ? highRatedPriceMultiplier : defaultRateMultiplier;
		final double seatPrice = baseSeatPrice * rateMultiplier;
		final double vipSeatPrice = vipSeatPriceMultiplier * seatPrice;
		final double discount = discountService.getDiscount(user, event);

		validateSeats(seats, auditorium);

		final List<Integer> auditoriumVipSeats = auditorium.getVipSeatsList();
		final List<Integer> vipSeats = auditoriumVipSeats.stream().filter(seats::contains).collect(
			Collectors.toList());
		final List<Integer> simpleSeats = seats.stream().filter(seat -> !vipSeats.contains(seat)).collect(
			Collectors.toList());

		final double simpleSeatsPrice = simpleSeats.size() * seatPrice;
		final double vipSeatsPrice = vipSeats.size() * vipSeatPrice;

		//        LOG.info("auditoriumVipSeats = " + auditoriumVipSeats);
		//        LOG.info("baseSeatPrice = " + baseSeatPrice);
		//        LOG.info("rateMultiplier = " + rateMultiplier);
		//        LOG.info("vipSeatPriceMultiplier = " + vipSeatPriceMultiplier);
		//        LOG.info("seatPrice = " + seatPrice);
		//        LOG.info("vipSeatPrice = " + vipSeatPrice);
		//        LOG.info("discount = " + discount);
		//        LOG.info("seats = " + seats);
		//        LOG.info("simpleSeats.size() = " + simpleSeats.size());
		//        LOG.info("vipSeats.size() = " + vipSeats.size());

		final double totalPrice = simpleSeatsPrice + vipSeatsPrice;

		return (1.0 - discount) * totalPrice;
	}

	private void validateSeats(List<Integer> seats, Auditorium auditorium) {
		final int seatsNumber = auditorium.getSeatsNumber();
		final Optional<Integer> incorrectSeat = seats.stream().filter(
			seat -> seat < minSeatNumber || seat > seatsNumber).findFirst();
		incorrectSeat.ifPresent(seat -> {
			throw new IllegalArgumentException(
				String.format("Seat: [%s] is incorrect. Auditorium: [%s] has [%s] seats", seat, auditorium.getName(),
					seatsNumber));
		});
	}

	@Override
	public Ticket bookTicket(User user, Ticket ticket) {
		if (Objects.isNull(user)) {
			throw new NullPointerException("User is [null]");
		}
		User foundUser = userService.getById(user.getId());
		if (Objects.isNull(foundUser)) {
			throw new IllegalStateException("User: [" + user + "] is not registered");
		}

		Stream<Ticket> bookedTickets = bookingsToTickets(bookingRepository.getAllByTicketEvent(ticket.getEvent()));
		Ticket finalTicket = ticket;
		boolean seatsAreAlreadyBooked = bookedTickets
			.anyMatch(bookedTicket -> finalTicket.getSeatsList().stream().anyMatch(
				bookedTicket.getSeatsList()::contains));

		if (!seatsAreAlreadyBooked) {
			ticket.setUser(user);
			ticket = bookingRepository.save(new Booking(user, ticket)).getTicket();
		} else {
			throw new IllegalStateException("Unable to book ticket: [" + ticket + "]. Seats are already booked.");
		}

		return ticket;
	}

	@Override
	public List<Ticket> getTicketsForEvent(String event, String auditoriumName, LocalDateTime date) {
		final Auditorium auditorium = auditoriumService.getByName(auditoriumName);
		final Event foundEvent = eventService.getEvent(event, auditorium, date);
		return bookingsToTickets(bookingRepository.getAllByTicketEvent(foundEvent)).collect(Collectors.toList());
	}

	@Override
	public Ticket bookTicket(Long eventId, LocalDateTime time, List<Integer> seats) {
		User user = userService.getCurrentUser();
		Event event = eventService.getById(eventId);
		double price = getTicketPrice(event.getName(), event.getAuditorium().getName(),
			time, seats, user);

		Ticket ticket = new Ticket(event, time, seats, user, price);

		return bookTicket(user, ticket);
	}

	private Stream<Ticket> bookingsToTickets(List<Booking> bookings) {
		return bookings.stream().map(Booking::getTicket);
	}
}
