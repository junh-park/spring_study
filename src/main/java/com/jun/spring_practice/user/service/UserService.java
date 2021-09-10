package com.jun.spring_practice.user.service;

import com.jun.spring_practice.user.entity.User;

public interface UserService {
	void upgradeLevels();

	void add(User user);
}
