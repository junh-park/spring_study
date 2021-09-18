package com.jun.spring_practice.user.sqlservice;

import java.util.Map;

import com.jun.spring_practice.exception.SqlUpdateFailureException;

public interface UpdatableSqlRegistry extends SqlRegistry {
	public void updateSql(String key, String sql) throws SqlUpdateFailureException;
	public void updateSql(Map<String, String> sqlmap) throws SqlUpdateFailureException;

}
