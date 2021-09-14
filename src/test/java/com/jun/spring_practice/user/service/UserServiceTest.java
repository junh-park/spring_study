package com.jun.spring_practice.user.service;

import static com.jun.spring_practice.user.service.NormalUserUpgradePolicy.MIN_LOGCOUNT_FOR_SILVER;
import static com.jun.spring_practice.user.service.NormalUserUpgradePolicy.MIN_RECOMMEND_GOLD;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import com.jun.spring_practice.exception.TestUserServiceException;
import com.jun.spring_practice.user.dao.UserDao;
import com.jun.spring_practice.user.domain.Level;
import com.jun.spring_practice.user.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class UserServiceTest {

	@Autowired
	ApplicationContext context;
	@Autowired
	UserService userService;
	@Autowired
	UserService testUserService;
	@Autowired
	UserDao userDao;
	@Autowired
	PlatformTransactionManager transactionManager;
	@Autowired
	MailSender mailSender;
	List<User> users;

	@Before
	public void setUp() {
		users = Arrays.asList(
				new User("1", "jun", "p1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER - 1, 0, "jun@hotmail.com"),
				new User("2", "hong", "p2", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0, "jun@hotmail.com"),
				new User("3", "park", "p3", Level.SILVER, 60, MIN_RECOMMEND_GOLD - 1, "jun@hotmail.com"),
				new User("4", "seo", "p4", Level.SILVER, 60, MIN_RECOMMEND_GOLD, "jun@hotmail.com"),
				new User("5", "guang", "p5", Level.GOLD, 100, Integer.MAX_VALUE, "jun@hotmail.com"));
	}
	
	@Test
	public void upgradeLevels() throws Exception {
		userDao.deleteAll();
		for (User user : users) {
			userDao.add(user);
		}

		userService.upgradeLevels();

		checkLevelUpdated(users.get(0), false);
		checkLevelUpdated(users.get(1), true);
		checkLevelUpdated(users.get(2), false);
		checkLevelUpdated(users.get(3), true);
		checkLevelUpdated(users.get(4), false);
	}

	@Test
	public void add() {
		userDao.deleteAll();

		User userWithLevel = users.get(4);
		User userWithoutLevel = users.get(0);
		userWithoutLevel.setLevel(null);

		userService.add(userWithLevel);
		userService.add(userWithoutLevel);

		User userWithLevelRead = userDao.get(userWithLevel.getId());
		User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

		assertThat(userWithLevelRead.getLevel(), is(userWithLevel.getLevel()));
		assertThat(userWithoutLevelRead.getLevel(), is(Level.BASIC));
	}

	@Test
	public void upgradeAllOrNothing() throws Exception {
		userDao.deleteAll();

		for (User user : users) {
			userDao.add(user);
		}

		try {
			this.testUserService.upgradeLevels();
			fail("TestUserServiceException expected");
		} catch (TestUserServiceException e) {
		}
		checkLevelUpdated(users.get(1), false);
	}

	@Test
	public void mockUpgradeLevels() {
		userDao.deleteAll();

		UserServiceImpl userServiceImpl = new UserServiceImpl();

		UserDao mockUserDao = mock(UserDao.class);
		when(mockUserDao.getAll()).thenReturn(this.users);
		userServiceImpl.setUserDao(mockUserDao);

		MailSender mockMailSender = mock(MailSender.class);
		userServiceImpl.setMailSender(mockMailSender);

		userServiceImpl.upgradeLevels();

		verify(mockUserDao, times(2)).update(any(User.class));
		verify(mockUserDao, times(2)).update(any(User.class));
		verify(mockUserDao).update(users.get(1));
		assertThat(users.get(1).getLevel(), is(Level.SILVER));
		verify(mockUserDao).update(users.get(3));
		assertThat(users.get(3).getLevel(), is(Level.GOLD));

		ArgumentCaptor<SimpleMailMessage> mailMessageArg = ArgumentCaptor.forClass(SimpleMailMessage.class);
		verify(mockMailSender, times(2)).send(mailMessageArg.capture());
		List<SimpleMailMessage> mailMessages = mailMessageArg.getAllValues();
		assertThat(mailMessages.get(0).getTo()[0], is(users.get(1).getEmail()));
		assertThat(mailMessages.get(1).getTo()[0], is(users.get(3).getEmail()));

	}

	@Test
	@DirtiesContext
	public void upgradeLevelsSendingEmail() {
		userDao.deleteAll();

		for (User user : users) {
			userDao.add(user);
		}

		MockMailSender mockMailSender = new MockMailSender();
		userService.setMailSender(mockMailSender);

		userService.upgradeLevels();

		checkLevelUpdated(users.get(0), false);
		checkLevelUpdated(users.get(1), true);
		checkLevelUpdated(users.get(2), false);
		checkLevelUpdated(users.get(3), true);
		checkLevelUpdated(users.get(4), false);

		List<String> requests = mockMailSender.getRequests();
		assertThat(requests.size(), is(2));
		assertThat(requests.get(0), is(users.get(1).getEmail()));
		assertThat(requests.get(1), is(users.get(3).getEmail()));
	}

 
	static class MockMailSender implements MailSender {
		private List<String> requests = new ArrayList<String>();

		public List<String> getRequests() {
			return requests;
		}

		public void send(SimpleMailMessage mailMessage) throws MailException {
			requests.add(mailMessage.getTo()[0]);
		}

		public void send(SimpleMailMessage... simpleMessages) throws MailException {

		}
	}

	private void checkLevelUpdated(User user, boolean upgraded) {
		User userUpdate = userDao.get(user.getId());
		if (upgraded) {
			assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
		} else {
			assertThat(userUpdate.getLevel(), is(user.getLevel()));
		}
	}
}
