package com.jun.spring_practice;

import java.sql.SQLException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.jun.spring_practice.connectionmaker.CountingConnectionMaker;
import com.jun.spring_practice.dao.UserDao;
import com.jun.spring_practice.entity.User;
import com.jun.spring_practice.factory.CountingDaoFactory;

public class UserDaoConnectionCountingTest {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
		UserDao userDao = context.getBean("userDao", UserDao.class);
		CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
		
		User user1 = new User("whiteship1", "jun", "married");
		
		userDao.add(user1);
		
		User user2 = new User("whiteship2", "jun", "married");
		
		userDao.add(user2);
		
		System.out.println(user1.getId()+" added successfully");
		System.out.println(ccm.getCounter());
	}
}
