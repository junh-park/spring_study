package com.jun.spring_practice.user.sqlservice;

import com.jun.spring_practice.exception.SqlNotFoundException;

public interface SqlRegistry {
	void registerSql(String key, String sql);
	
	String findSql(String key) throws SqlNotFoundException;
}
