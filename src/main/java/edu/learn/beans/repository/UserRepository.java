package edu.learn.beans.repository;

import edu.learn.beans.models.User;
import java.util.List;
import java.util.Objects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	static void validateUser(User user) {
		if (Objects.isNull(user)) {
			throw new NullPointerException("User is [null]");
		}
		if (Objects.isNull(user.getEmail())) {
			throw new NullPointerException("User's email is [null]. User: [" + user + "]");
		}
		if (Objects.isNull(user.getName())) {
			throw new NullPointerException("User's name is [null]. User: [" + user + "]");
		}
	}

	User getByEmail(String email);

	List<User> getAllByName(String name);
}
