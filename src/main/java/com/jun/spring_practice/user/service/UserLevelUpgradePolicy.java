package com.jun.spring_practice.user.service;

import com.jun.spring_practice.user.entity.User;

public interface UserLevelUpgradePolicy {
	boolean canUpgradeLevel(User user);
	void upgradeLevel(User user);
}
