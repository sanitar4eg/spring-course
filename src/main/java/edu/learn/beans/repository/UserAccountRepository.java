package edu.learn.beans.repository;

import edu.learn.beans.models.User;
import edu.learn.beans.models.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

	UserAccount findByUser(User user);
}
