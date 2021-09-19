package com.jun.spring_practice.user.service;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.jun.spring_practice.exception.SqlNotFoundException;
import com.jun.spring_practice.exception.SqlUpdateFailureException;
import com.jun.spring_practice.user.sqlservice.UpdatableSqlRegistry;

public abstract class AbstractUpdatableSqlRegistryTest {
	UpdatableSqlRegistry sqlRegistry;
	
	@Before
	public void setUp() {
		sqlRegistry = createUpdatableSqlRegistry();
		sqlRegistry.registerSql("KEY1", "SQL1");
		sqlRegistry.registerSql("KEY2", "SQL2");
		sqlRegistry.registerSql("KEY3", "SQL3");
	}
	
	protected abstract UpdatableSqlRegistry createUpdatableSqlRegistry();

	@Test
	public void find() {
		checkIFndResult("SQL1", "SQL2", "SQL3");
	}
	
	@Test(expected=SqlNotFoundException.class)
	public void unknownKey() {
		sqlRegistry.findSql("SQL0000");
	}
	
	@Test
	public void updateSingle() {
		sqlRegistry.updateSql("KEY2", "Modified2");
		checkIFndResult("SQL1", "Modified2", "SQL3");
	}
	
	@Test
	public void updateMulti() {
		Map<String, String> sqlmap = new HashMap<String, String>();
		sqlmap.put("KEY1", "Modified1");
		sqlmap.put("KEY3", "Modified3");
		
		sqlRegistry.updateSql(sqlmap);
		checkIFndResult("Modified1", "SQL2", "Modified3");
	}

	@Test(expected=SqlUpdateFailureException.class)
	public void updateWithNotExsitingKey() {
		sqlRegistry.updateSql("SQL99999", "Modified2");
	}

	protected void checkIFndResult(String expected1, String expected2, String expected3) {
		assertThat(sqlRegistry.findSql("KEY1"), is(expected1));
		assertThat(sqlRegistry.findSql("KEY2"), is(expected2));
		assertThat(sqlRegistry.findSql("KEY3"), is(expected3));
	}
}
