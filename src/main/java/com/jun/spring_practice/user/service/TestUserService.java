package com.jun.spring_practice.user.service;

import com.jun.spring_practice.exception.TestUserServiceException;
import com.jun.spring_practice.user.entity.User;

public class TestUserService extends UserServiceImpl {
	private String id;

	TestUserService(String id) {
		this.id = id;
	}

	protected void upgradeLevel(User user) {
		if(user.getId().equals(this.id)) throw new TestUserServiceException();
		super.upgradeLevel(user);
	}
}
