package com.jun.spring_practice.learningtest.pointcut;

public interface TargetInterface {
	public void hello();
	public void hello(String a);
	public int minus(int a, int b) throws RuntimeException;
	public int plus(int a, int b);
}
