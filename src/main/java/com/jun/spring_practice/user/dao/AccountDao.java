package com.jun.spring_practice.user.dao;

import com.jun.spring_practice.connectionmaker.ConnectionMaker;

public class AccountDao{

	private ConnectionMaker connectionMaker;

	public AccountDao(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}

}
