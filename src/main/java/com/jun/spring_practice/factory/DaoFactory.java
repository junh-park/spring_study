package com.jun.spring_practice.factory;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.jun.spring_practice.connectionmaker.MConnectionMaker;
import com.jun.spring_practice.connectionmaker.OConnectionMaker;
import com.jun.spring_practice.dao.AccountDao;
import com.jun.spring_practice.dao.MessageDao;
import com.jun.spring_practice.dao.UserDao;

@Configuration
public class DaoFactory {

	@Bean
	public UserDao userDao() {
		UserDao userDao = new UserDao();
		userDao.setDataSource(dataSource());
		return userDao;
	}
	
//	@Bean
//	public DataSource dataSource() {
//		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
//		dataSource.setDriverClass(oracle.jdbc.driver.OracleDriver.class);
//		dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
//		dataSource.setUsername("Trainee1");
//		dataSource.setPassword("!QAZSE4");
//		
//		return dataSource;
//	}
	
	@Bean
	public DataSource dataSource() {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		
//		dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
		dataSource.setUrl("jdbc:mysql://localhost:3306/spring_toby");
		dataSource.setUsername("root");
		dataSource.setPassword("eltemia");
		
		return dataSource;
	}
	
	
	public MessageDao messageDao() {
		return new MessageDao(connectionMaker());
	}
	
	public AccountDao accountDao() {
		return new AccountDao(connectionMaker());
	}

	@Bean
	public OConnectionMaker connectionMaker() {
		return new OConnectionMaker();
	}

}
