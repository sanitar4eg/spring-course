package edu.learn.beans.services;

import edu.learn.beans.models.Ticket;
import edu.learn.beans.models.User;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

	User register(User user);

	void remove(User user);

	User getById(Long id);

	User getUserByEmail(String email);

	List<User> getUsersByName(String name);

	List<Ticket> getBookedTickets();

	User getCurrentUser();
}
