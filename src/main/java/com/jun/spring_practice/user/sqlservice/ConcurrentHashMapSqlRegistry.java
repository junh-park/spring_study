package com.jun.spring_practice.user.sqlservice;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.jun.spring_practice.exception.SqlNotFoundException;
import com.jun.spring_practice.exception.SqlUpdateFailureException;

public class ConcurrentHashMapSqlRegistry implements UpdatableSqlRegistry {
	private Map<String, String> sqlMap = new ConcurrentHashMap<String, String>();
	
	public String findSql(String key) throws SqlNotFoundException {
		String sql = sqlMap.get(key);
		if(sql == null) throw new SqlNotFoundException("Failed to find the sql using the key " + key);
		else return sql;
	}

	public void registerSql(String key, String sql) {
		sqlMap.put(key, sql);
	}

	public void updateSql(String key, String sql) throws SqlUpdateFailureException {
		if(sqlMap.get(key) == null) {
			throw new SqlNotFoundException("Failed to find the sql using the key " + key);
		}
		sqlMap.put(key, sql);
	}

	public void updateSql(Map<String, String> sqlmap) throws SqlUpdateFailureException {
		for (Map.Entry<String, String> entry : sqlmap.entrySet()) {
			sqlmap.put(entry.getKey(), entry.getValue());
		}
	}

	
}
