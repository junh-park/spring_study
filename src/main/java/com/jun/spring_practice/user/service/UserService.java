package com.jun.spring_practice.user.service;

import java.util.List;

import com.jun.spring_practice.user.dao.UserDao;
import com.jun.spring_practice.user.domain.Level;
import com.jun.spring_practice.user.entity.User;

public class UserService {
	UserDao userDao;
	UserLevelUpgradePolicy upgradePolicy;

	public void add(User user) {
		if (user.getLevel() == null)
			user.setLevel(Level.BASIC);
		userDao.add(user);
	}

	public void upgradeLevels() {
		List<User> users = userDao.getAll();
		for (User user : users) {
			if (upgradePolicy.canUpgradeLevel(user)) {
				upgradePolicy.upgradeLevel(user);
			}
		}
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setUserLevelUpgradePolicy(UserLevelUpgradePolicy upgradePolicy) {
		this.upgradePolicy = upgradePolicy;
	}
}
