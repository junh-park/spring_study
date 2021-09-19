package com.jun.spring_practice.exception;

public class SqlUpdateFailureException extends RuntimeException {
	public SqlUpdateFailureException(String message) {
		super(message);
	}

	public SqlUpdateFailureException(Throwable e) {
		super(e);
	}

	public SqlUpdateFailureException(String message, Throwable e) {
		super(message, e);
	}
}
