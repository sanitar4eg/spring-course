package edu.learn.beans.services;

import edu.learn.beans.models.Ticket;
import edu.learn.beans.models.User;
import edu.learn.beans.repository.UserRepository;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA. User: Dmytro_Babichev Date: 2/1/2016 Time: 7:30 PM
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User register(User user) {
		if (Objects.nonNull(userRepository.getByEmail(user.getEmail()))) {
			throw new IllegalStateException("User with same email exist in database");
		}
		return userRepository.save(user);
	}

	@Transactional
	public void remove(User user) {
		user = userRepository.getByEmail(user.getEmail());
		userRepository.delete(user);
		userRepository.flush();
	}

	public User getById(Long id) {
		return userRepository.getOne(id);
	}

	public User getUserByEmail(String email) {
		return userRepository.getByEmail(email);
	}

	public List<User> getUsersByName(String name) {
		return userRepository.getAllByName(name);
	}

	public List<Ticket> getBookedTickets() {
		throw new UnsupportedOperationException("not implemented yet");
	}
}
