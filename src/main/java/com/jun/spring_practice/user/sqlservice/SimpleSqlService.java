package com.jun.spring_practice.user.sqlservice;

import java.util.Map;

import com.jun.spring_practice.exception.SqlRetrievalFailureException;

public class SimpleSqlService implements SqlService{
	private Map<String, String> sqlMap;
	
	public void setSqlMap(Map<String, String> sqlMap) {
		this.sqlMap = sqlMap;
	}

	public String getSql(String key) throws SqlRetrievalFailureException {
		String sql = sqlMap.get(key);
		if(sql == null) {
			throw new SqlRetrievalFailureException(key + " cannot be found");
		} else {
			return sql;
		}
	}
}
