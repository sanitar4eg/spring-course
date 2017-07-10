package edu.learn.beans.controllers;

import edu.learn.beans.models.Event;
import edu.learn.beans.models.User;
import edu.learn.beans.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(method = RequestMethod.GET, params = "id")
	public User getById(@RequestParam Long id) {
		return userService.getById(id);
	}

	@RequestMapping(method = RequestMethod.GET, params = "email")
	public User getUserByEmail(@RequestParam String email) {
		return userService.getUserByEmail(email);
	}

	@RequestMapping(method = RequestMethod.GET, params = "name")
	public List<User> getUsersByName(@RequestParam String name) {
		return userService.getUsersByName(name);
	}

	@RequestMapping(method = RequestMethod.POST)
	public List<Event> register() {
//		return userService.register();
//		TODO: Implement
		return null;
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public void remove(@RequestParam Long id) {
		User user = userService.getById(id);
		userService.remove(user);
	}

}
