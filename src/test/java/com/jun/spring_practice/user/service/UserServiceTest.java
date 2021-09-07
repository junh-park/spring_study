package com.jun.spring_practice.user.service;

import static com.jun.spring_practice.user.service.NormalUserUpgradePolicy.MIN_LOGCOUNT_FOR_SILVER;
import static com.jun.spring_practice.user.service.NormalUserUpgradePolicy.MIN_RECOMMEND_GOLD;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jun.spring_practice.user.dao.UserDao;
import com.jun.spring_practice.user.domain.Level;
import com.jun.spring_practice.user.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class UserServiceTest {

	@Autowired
	UserService userService;
	@Autowired
	UserDao userDao;
	List<User> users;

	@Before
	public void setUp() {
		users = Arrays.asList(
				new User("1", "jun", "p1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER-1, 0),
				new User("2", "hong", "p2", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0),
				new User("3", "park", "p3", Level.SILVER, 60, MIN_RECOMMEND_GOLD-1),
				new User("4", "seo", "p4", Level.SILVER, 60, MIN_RECOMMEND_GOLD),
				new User("5", "guang", "p5", Level.GOLD, 100, Integer.MAX_VALUE)
				); 
		
		userDao.deleteAll();
	}

	@Test
	public void upgradeLevels() {
		for (User user : users) {
			userDao.add(user);
		}
		
		userService.upgradeLevels();
		
		checkLevel(users.get(0), false);
		checkLevel(users.get(1), true);
		checkLevel(users.get(2), false);
		checkLevel(users.get(3), true);
		checkLevel(users.get(4), false);
	}
	
	@Test
	public void add() {
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

	private void checkLevel(User user, boolean upgraded) {
		User userUpdate = userDao.get(user.getId());
		if(upgraded) {
			assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
		} else {
			assertThat(userUpdate.getLevel(), is(user.getLevel()));
		}
	}
}
