package edu.learn.beans.services;

import edu.learn.beans.models.Ticket;
import edu.learn.beans.models.User;
import edu.learn.beans.repository.UserRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(UserRepository userRepository,
		PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User register(User user) {
		if (Objects.nonNull(userRepository.getByEmail(user.getEmail()))) {
			throw new IllegalStateException("User with same email exist in database");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
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

	@Override
	public User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object user = auth.getPrincipal();
		if (Objects.nonNull(user) && user instanceof User) {
			return (User) user;
		} else {
			throw new AccessDeniedException("User is not authenticated");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return Optional.ofNullable(userRepository.getByEmail(username))
			.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}
}
