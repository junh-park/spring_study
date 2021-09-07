package com.jun.spring_practice.dao;

import java.util.List;

import com.jun.spring_practice.entity.User;

public interface UserDao {
	void add(User user);
	User get(String id);
	List<User> getAll();
	void deleteAll();
	int getCount();
}
