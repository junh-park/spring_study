package com.jun.spring_practice.user.service;

import java.util.List;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.jun.spring_practice.user.dao.UserDao;
import com.jun.spring_practice.user.domain.Level;
import com.jun.spring_practice.user.entity.User;

public class UserService {
	UserDao userDao;
	private PlatformTransactionManager transactionManager;
	private MailSender mailSender;
	
//	UserLevelUpgradePolicy upgradePolicy;
	public static final int MIN_RECOMMEND_GOLD = 30;
	public static final int MIN_LOGCOUNT_FOR_SILVER = 50;

	public void add(User user) {
		if (user.getLevel() == null)
			user.setLevel(Level.BASIC);
		userDao.add(user);
	}

	public void upgradeLevels() {
		TransactionStatus txStatus = this.transactionManager.getTransaction(new DefaultTransactionDefinition());

		try {
			List<User> users = userDao.getAll();
			for (User user : users) {
				if (canUpgradeLevel(user)) {
					upgradeLevel(user);
				}
			}
			this.transactionManager.commit(txStatus);
		} catch (RuntimeException e) {
			this.transactionManager.rollback(txStatus);
			throw e;
		} 
	}

	protected void upgradeLevel(User user) {
		user.upgradeLevel();
		userDao.update(user);
		sendUpgradeEmail(user);
	}

	private void sendUpgradeEmail(User user) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(user.getEmail());
		mailMessage.setFrom("useradmin@hotmail.org");
		mailMessage.setSubject("Upgrade Notification");
		mailMessage.setText("Your user level has now lifted to " + user.getLevel().name());
		
		mailSender.send(mailMessage);
	}

	public boolean canUpgradeLevel(User user) {
		Level currentLevel = user.getLevel();
		switch (currentLevel) {
		case BASIC:
			return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
		case SILVER:
			return (user.getRecommend() >= MIN_RECOMMEND_GOLD);
		case GOLD:
			return false;
		default:
			throw new IllegalArgumentException("Unknown Level: " + currentLevel);
		}
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

//	public void setUserLevelUpgradePolicy(UserLevelUpgradePolicy upgradePolicy) {
//		this.upgradePolicy = upgradePolicy;
//	}
}
