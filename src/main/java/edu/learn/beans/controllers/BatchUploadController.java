package edu.learn.beans.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.learn.beans.models.Event;
import edu.learn.beans.models.User;
import edu.learn.beans.services.EventService;
import edu.learn.beans.services.UserService;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("upload")
public class BatchUploadController {

	private static final Logger LOG = LoggerFactory.getLogger(BatchUploadController.class);

	private final ObjectMapper objectMapper;

	private final UserService userService;

	private final EventService eventService;

	@Autowired
	public BatchUploadController(ObjectMapper objectMapper, UserService userService, EventService eventService) {
		this.objectMapper = objectMapper;
		this.userService = userService;
		this.eventService = eventService;
	}

	@RequestMapping
	public String upload(Model model) {
		model.addAttribute("message", "");
		return "upload";
	}

	@PostMapping("users")
	public String usersUpload(@RequestParam("file") MultipartFile file) throws IOException {

		User[] users = objectMapper.readValue(file.getInputStream(), User[].class);
		List<User> list = Arrays.asList(users);
		list.forEach(user -> {
			userService.register(user);
			LOG.info("User registered: {}", userService.getUserByEmail(user.getEmail()));
		});

		return "upload";
	}

	@PostMapping("events")
	public String eventsUpload(@RequestParam("file") MultipartFile file) throws IOException {

		Event[] events = objectMapper.readValue(file.getInputStream(), Event[].class);
		List<Event> list = Arrays.asList(events);
		list.forEach(event -> {
			eventService.create(event);
			LOG.info("Event created: {}", eventService.getByName(event.getName()));
		});

		return "upload";
	}


}
