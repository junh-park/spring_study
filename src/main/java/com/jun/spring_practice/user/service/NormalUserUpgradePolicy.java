package com.jun.spring_practice.user.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.jun.spring_practice.user.dao.UserDao;
import com.jun.spring_practice.user.domain.Level;
import com.jun.spring_practice.user.entity.User;

public class NormalUserUpgradePolicy implements UserLevelUpgradePolicy {
	@Autowired
	UserDao userDao;
	
	public static final int MIN_RECOMMEND_GOLD = 30;
	public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
	
	public void upgradeLevel(User user) {
		user.upgradeLevel();
		userDao.update(user);
	}

	public boolean canUpgradeLevel(User user) {
		Level currentLevel = user.getLevel();
		switch (currentLevel) {
		case BASIC: return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
		case SILVER: return (user.getRecommend() >= MIN_RECOMMEND_GOLD);	
		case GOLD: return false;
		default: throw new IllegalArgumentException("Unknown Level: " + currentLevel);
		}
	}
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
