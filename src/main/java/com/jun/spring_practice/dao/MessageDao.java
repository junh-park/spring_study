package com.jun.spring_practice.dao;

import com.jun.spring_practice.connectionmaker.ConnectionMaker;

public class MessageDao {

	private ConnectionMaker connectionMaker;

	public MessageDao(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}

}
