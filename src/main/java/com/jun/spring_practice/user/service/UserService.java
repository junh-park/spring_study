package com.jun.spring_practice.user.service;

import org.springframework.mail.MailSender;

import com.jun.spring_practice.user.entity.User;

public interface UserService {
	void upgradeLevels();

	void add(User user);

	void setMailSender(MailSender mailSender);
}
