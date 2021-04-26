package com.jun.spring_practice.factory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jun.spring_practice.connectionmaker.ConnectionMaker;
import com.jun.spring_practice.connectionmaker.CountingConnectionMaker;
import com.jun.spring_practice.connectionmaker.MConnectionMaker;
import com.jun.spring_practice.dao.UserDao;

@Configuration
public class CountingDaoFactory {
	
	@Bean
	public UserDao userDao() {
		UserDao userDao = new UserDao();
//		userDao.setDataSource(dataSource);;
		return userDao;
	}

	@Bean
	public ConnectionMaker connectionMaker() {
		return new CountingConnectionMaker(realConnectionMaker());
	}

	public ConnectionMaker realConnectionMaker() {
		return new MConnectionMaker();
	}
}
