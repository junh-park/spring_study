package com.jun.spring_practice.user.sqlservice;

import com.jun.spring_practice.exception.SqlRetrievalFailureException;

public interface SqlService {
	String getSql(String key) throws SqlRetrievalFailureException;
}
