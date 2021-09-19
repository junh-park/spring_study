package com.jun.spring_practice.user.dao.learningtest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

import java.util.List;
import java.util.Map;

import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.datasource.embedded.*;

public class EmbeddedDbTest {
	EmbeddedDatabase db;
	JdbcTemplate template;

	@Before
	public void setUp() {
		db = new EmbeddedDatabaseBuilder()
				.setType(HSQL)
				.addScripts("classpath:/schema.sql", "classpath:/data.sql")
				.build();
		
		template = new SimpleJdbcCall(db).getJdbcTemplate();
	}
	
	@After
	public void tearDown() {
		db.shutdown();
	}

	@Test
	public void initDate() {
		assertThat(template.queryForObject("select count(*) from sqlmap", Integer.class), is(2));
		
		List<Map<String,Object>> list = template.queryForList("select * from sqlmap order by key_");
		assertThat((String) list.get(0).get("key_"), is("KEY1"));
		assertThat((String) list.get(0).get("sql_"), is("SQL1"));
		assertThat((String) list.get(1).get("key_"), is("KEY2"));
		assertThat((String) list.get(1).get("sql_"), is("SQL2"));
	}
	
	@Test
	public void insert() {
		template.update("insert into sqlmap(key_,sql_) values(?,?)", "KEY3", "SQL3");
		
		assertThat(template.queryForObject("select count(*) from sqlmap", Integer.class), is(3));
	}
}
