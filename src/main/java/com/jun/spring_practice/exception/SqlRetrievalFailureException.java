package com.jun.spring_practice.exception;

public class SqlRetrievalFailureException extends RuntimeException {
	public SqlRetrievalFailureException(String message) {
		super(message);
	}
	
	public SqlRetrievalFailureException(Throwable cause) {
		super(cause);
	}
	
	public SqlRetrievalFailureException(String message, Throwable cause) {
		super(message, cause);
	}
}
