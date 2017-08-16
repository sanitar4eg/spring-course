package edu.learn.beans.services;

import edu.learn.beans.models.Event;
import edu.learn.beans.models.User;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchImportServiceImpl implements BatchImportService {

	private static final Logger LOG = LoggerFactory.getLogger(BatchImportServiceImpl.class);

	private final EventService eventService;
	private final UserService userService;

	@Autowired
	public BatchImportServiceImpl(EventService eventService, UserService userService) {
		this.eventService = eventService;
		this.userService = userService;
	}

	@Override
	public void importEvents(List<Event> events) {
		events.forEach(event -> {
			eventService.create(event);
			LOG.info("Event created: {}", eventService.getByName(event.getName()));
		});
	}

	@Override
	public void importUsers(List<User> users) {
		users.forEach(user -> {
			userService.register(user);
			LOG.info("User registered: {}", userService.getUserByEmail(user.getEmail()));
		});
	}
}
