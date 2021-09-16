package com.jun.spring_practice.user.service;

import java.util.List;

import org.springframework.dao.TransientDataAccessResourceException;

import com.jun.spring_practice.exception.TestUserServiceException;
import com.jun.spring_practice.user.entity.User;

public class TestUserServiceImpl extends UserServiceImpl {
	private String id = "4";

	protected void upgradeLevel(User user) {
		if (user.getId().equals(this.id))
			throw new TestUserServiceException();
		super.upgradeLevel(user);
	}

	public List<User> getAll() {
		for (User user : super.getAll()) {
			try {
				System.out.println("Attempting to update the user " + user.getId());
				super.update(user);  
			} catch (TransientDataAccessResourceException e) {
				e.getRootCause();
				System.out.println("Failed to update due to the read only attribute");
				throw e;
			}
		}
		return null;
	}
}
