package com.jun.spring_practice.user.service;

import java.util.List;

import org.springframework.mail.MailSender;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.jun.spring_practice.user.entity.User;

public class UserServiceTx implements UserService {
	PlatformTransactionManager transactionManager;
	UserService userService;

	public void add(User user) {
		this.userService.add(user);
	}

	public void upgradeLevels() {
		TransactionStatus txStatus = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			userService.upgradeLevels();
			this.transactionManager.commit(txStatus);
		} catch (RuntimeException e) {
			this.transactionManager.rollback(txStatus);
			throw e;
		}
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List<User> getAll() {
		return null;
	}

	public User get(String id) {
		return null;
	}

	public void deleteAll() {
	}

	public void update(User user) {
	}
}
