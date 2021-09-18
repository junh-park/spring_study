package com.jun.spring_practice.user.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Before;
import org.junit.Test;

import com.jun.spring_practice.exception.SqlNotFoundException;
import com.jun.spring_practice.exception.SqlUpdateFailureException;
import com.jun.spring_practice.user.sqlservice.ConcurrentHashMapSqlRegistry;
import com.jun.spring_practice.user.sqlservice.UpdatableSqlRegistry;

public class ConcurrentHashMapSqlRegistryTest {
	UpdatableSqlRegistry sqlRegistry;
	
	@Before
	public void setUp() {
		sqlRegistry = new ConcurrentHashMapSqlRegistry();
		sqlRegistry.registerSql("KEY1", "SQL1");
		sqlRegistry.registerSql("KEY2", "SQL2");
		sqlRegistry.registerSql("KEY3", "SQL3");
	}
	
	@Test
	public void find() {
		checkFIndResult("SQL1", "SQL2", "SQL3");
	}

	private void checkFIndResult(String expected1, String expected2, String expected3) {
		assertThat(sqlRegistry.findSql(expected1), is(expected1));
		assertThat(sqlRegistry.findSql(expected2), is(expected2));
		assertThat(sqlRegistry.findSql(expected3), is(expected3));
	}
	
	@Test(expected=SqlNotFoundException.class)
	public void unknownKey() {
		sqlRegistry.findSql("SQL0000");
	}
	
	@Test
	public void updateSingle() {
		sqlRegistry.updateSql("KEY2", "Modified2");
		checkFIndResult("SQL1", "Modified2", "SQL3");
	}
	
	@Test
	public void updateMulti() {
		Map<String, String> sqlmap = new HashMap<String, String>();
		sqlmap.put("KEY1", "Modified1");
		sqlmap.put("KEY3", "Modified3");
		
		sqlRegistry.updateSql(sqlmap);
		checkFIndResult("Modified1", "SQL2", "Modified3");
	}

	@Test(expected=SqlUpdateFailureException.class)
	public void updateWithNotExsitingKey() {
		sqlRegistry.updateSql("SQL99999", "Modified2");
	}
}
