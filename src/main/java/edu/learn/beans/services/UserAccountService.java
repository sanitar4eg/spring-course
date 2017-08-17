package edu.learn.beans.services;

import edu.learn.beans.models.User;
import edu.learn.beans.models.UserAccount;

public interface UserAccountService {

	void save(UserAccount userAccount);

	UserAccount findByUser(User user);

	UserAccount addMoney(User user, Double money);

}
