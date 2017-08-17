package edu.learn.beans.services;

import edu.learn.beans.models.User;
import edu.learn.beans.models.UserAccount;
import edu.learn.beans.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements UserAccountService {

	private final UserAccountRepository userAccountRepository;

	@Autowired
	public UserAccountServiceImpl(UserAccountRepository userAccountRepository) {
		this.userAccountRepository = userAccountRepository;
	}

	@Override
	public void save(UserAccount userAccount) {
		userAccountRepository.save(userAccount);
	}

	@Override
	public UserAccount findByUser(User user) {
		return userAccountRepository.findByUser(user);
	}

	@Override
	public UserAccount addMoney(User user, Double money) {
		UserAccount account = userAccountRepository.findByUser(user);
		account.setMoney(account.getMoney() + money);
		return userAccountRepository.save(account);
	}
}
