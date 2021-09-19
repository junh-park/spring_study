package com.jun.spring_practice.user.service;

import com.jun.spring_practice.user.sqlservice.ConcurrentHashMapSqlRegistry;
import com.jun.spring_practice.user.sqlservice.UpdatableSqlRegistry;

public class ConcurrentHashMapSqlRegistryTest extends AbstractUpdatableSqlRegistryTest {

	protected UpdatableSqlRegistry createUpdatableSqlRegistry() {
		return new ConcurrentHashMapSqlRegistry();
	}

}
