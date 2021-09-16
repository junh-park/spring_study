package com.jun.spring_practice.exception;

public class SqlNotFoundException extends RuntimeException {
	public SqlNotFoundException(String message) {
		super(message);
	}

	public SqlNotFoundException(Throwable e) {
		super(e);
	}

	public SqlNotFoundException(String message, Throwable e) {
		super(message, e);
	}
}
