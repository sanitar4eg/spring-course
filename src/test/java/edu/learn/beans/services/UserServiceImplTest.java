package edu.learn.beans.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import edu.learn.beans.TestConfiguration;
import edu.learn.beans.configuration.TestUserServiceConfiguration;
import edu.learn.beans.models.User;
import edu.learn.beans.repository.UserRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA. User: Dmytro_Babichev Date: 06/2/16 Time: 8:02 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfiguration.class, TestUserServiceConfiguration.class})
@Transactional
public class UserServiceImplTest {

	@Autowired
	private ConfigurableApplicationContext applicationContext;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@After
	public void cleanup() {
		userRepository.deleteAll();
	}

	@Test
	public void testRegister() throws Exception {
		String email = UUID.randomUUID().toString();
		User user = new User(email, UUID.randomUUID().toString(), LocalDate.now());
		long registeredId = userService.register(user).getId();
		assertEquals("User should be the same", userService.getUserByEmail(email), user.withId(registeredId));
	}

	@Test(expected = RuntimeException.class)
	public void testRegister_Exception() throws Exception {
		User testUser1 = (User) applicationContext.getBean("testUser1");
		userService.register(testUser1);
	}

	@Test
	public void testRemove() throws Exception {
		User testUser1 = (User) applicationContext.getBean("testUser1");
		userService.remove(testUser1);
		assertEquals("User should be the same", userService.getUserByEmail(testUser1.getEmail()), null);
	}

	@Test
	public void testUsersGetByName() throws Exception {
		User testUser1 = (User) applicationContext.getBean("testUser1");
		List<User> before = userService.getUsersByName(testUser1.getName());
		User addedUser = new User(UUID.randomUUID().toString(), testUser1.getName(), LocalDate.now());
		long registeredId = userService.register(addedUser).getId();
		List<User> after = userService.getUsersByName(testUser1.getName());
		before.add(addedUser.withId(registeredId));
		assertTrue("Users should change", before.containsAll(after));
		assertTrue("Users should change", after.containsAll(before));
	}

	@Test
	public void testGetUserByEmail() throws Exception {
		User testUser1 = (User) applicationContext.getBean("testUser1");
		User foundUser = userService.getUserByEmail(testUser1.getEmail());
		assertEquals("User should match", testUser1, foundUser);
	}

	@Test
	public void testGetUserByEmail_Null() throws Exception {
		User foundUser = userService.getUserByEmail(UUID.randomUUID().toString());
		assertNull("There should not be such user", foundUser);
	}
}
