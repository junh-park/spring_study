package com.jun.spring_practice.exception;

public class DuplicateUserIdException extends RuntimeException{
	public DuplicateUserIdException(Throwable cause) {
		super(cause);
	}
}
