package com.jun.spring_practice.user.service;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import static org.junit.Assert.fail;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Test;

import com.jun.spring_practice.exception.SqlUpdateFailureException;
import com.jun.spring_practice.user.sqlservice.EmbeddedDbSqlRegistry;
import com.jun.spring_practice.user.sqlservice.UpdatableSqlRegistry;

public class EmbeddedDbSqlRegistryTest extends AbstractUpdatableSqlRegistryTest {
	EmbeddedDatabase db;
	
	protected UpdatableSqlRegistry createUpdatableSqlRegistry() {
		db = new EmbeddedDatabaseBuilder().setType(HSQL).addScript("classpath:/schema.sql").build();
	
		EmbeddedDbSqlRegistry sqlRegistry = new EmbeddedDbSqlRegistry();
		sqlRegistry.setDataSource(db);
		
		return sqlRegistry;
	}
	
	@Test
	public void transactionUpdate() {
		checkIFndResult("SQL1", "SQL2", "SQL3");
		
		Map<String, String> sqlmap = new HashMap<String, String>();
		sqlmap.put("KEY1", "Modified1");
		sqlmap.put("KEY12341234!", "Modified9999");
		
		try {
			sqlRegistry.updateSql(sqlmap);
			fail();
		} catch (SqlUpdateFailureException e) {
			checkIFndResult("SQL1", "SQL2", "SQL3");
		}
	}
	
	@After
	public void tearDown() {
		db.shutdown();
	}
}
