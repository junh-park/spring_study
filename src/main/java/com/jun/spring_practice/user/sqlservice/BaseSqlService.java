package com.jun.spring_practice.user.sqlservice;

import javax.annotation.PostConstruct;

import com.jun.spring_practice.exception.SqlNotFoundException;
import com.jun.spring_practice.exception.SqlRetrievalFailureException;

public class BaseSqlService implements SqlService{
	private SqlReader sqlReader;
	private SqlRegistry sqlRegistry;

	public BaseSqlService() {
		setSqlReader(new JaxbXmlSqlReader());
		setSqlRegistry(new EmbeddedDbSqlRegistry());
	}
	
	@PostConstruct
	public void loadSql() {
		this.sqlReader.read(sqlRegistry);
	}

	public String getSql(String key) throws SqlRetrievalFailureException {
		try {
			return this.sqlRegistry.findSql(key);
		} catch (SqlNotFoundException e) {
			throw new SqlRetrievalFailureException(e);
		}
	}

	public void setSqlReader(SqlReader sqlReader) {
		this.sqlReader = sqlReader;
	}

	public void setSqlRegistry(SqlRegistry sqlRegistry) {
		this.sqlRegistry = sqlRegistry;
	}

}
