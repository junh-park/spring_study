package com.jun.spring_practice.learningtest.template;

public interface LineCallback<T> {
	public T doSomethingWithLine(String line, T value);
}
