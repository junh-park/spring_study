package com.jun.spring_practice.user.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.jun.spring_practice.user.entity.User;

@Transactional
public interface UserService {
	void upgradeLevels();
	void add(User user);
	void deleteAll();
	void update(User user);
	
	@Transactional(readOnly = true)
	List<User> getAll();
	@Transactional(readOnly = true)
	User get(String id);
}
