package com.jun.spring_practice.user.service;

import java.util.List;

import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.stereotype.Service;

import com.jun.spring_practice.exception.TestUserServiceException;
import com.jun.spring_practice.user.entity.User;

public class TestUserServiceImpl extends UserServiceImpl {
	private String id = "4";

	protected void upgradeLevel(User user) {
		if (user.getId().equals(this.id)) throw new TestUserServiceException();
		super.upgradeLevel(user);
	}

	public List<User> getAll() {
		for (User user : super.getAll()) {
				System.out.println("Attempting to update the user " + user.getId());
				super.update(user);  
		}
		return null;
	}
}
