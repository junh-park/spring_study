package com.jun.spring_practice;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jun.spring_practice.dao.UserDao;
import com.jun.spring_practice.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/applicationContext.xml")
public class UserDaoTest {

	@Autowired
	private UserDao dao;

	private User user1;
	private User user2;
	private User user3;

	@Before
	public void setUp() {
		this.user1 = new User("gyumee", "park", "springno1");
		this.user2 = new User("leeg78", "lee", "springno2");
		this.user3 = new User("bumji", "park", "springno3");
	}

	@Test 
	public void addAndGet() throws SQLException, ClassNotFoundException {
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));

		dao.add(user1);
		dao.add(user2);
 
		assertThat(dao.getCount(), is(2));

		User user4 = dao.get(user1.getId());

		assertThat(user4.getName(), is(user1.getName()));
		assertThat(user4.getPassword(), is(user1.getPassword()));

		User user5 = dao.get(user2.getId());

		assertThat(user5.getName(), is(user2.getName()));
		assertThat(user5.getPassword(), is(user2.getPassword()));
	}

	@Test
	public void count() throws SQLException {
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));

		dao.add(user1);
		assertThat(dao.getCount(), is(1));

		dao.add(user2);
		assertThat(dao.getCount(), is(2));

		dao.add(user3);
		assertThat(dao.getCount(), is(3));
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void getUserFailure() throws SQLException, ClassNotFoundException {
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));

		dao.get("unknown_id");
	}

//	public static void main(String[] args) {
//		JUnitCore.main("com.jun.spring_practice.UserDaoTest");
//	}
}
