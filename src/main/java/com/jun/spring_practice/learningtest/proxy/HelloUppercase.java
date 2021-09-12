package com.jun.spring_practice.learningtest.proxy;

public class HelloUppercase implements Hello {
	Hello helloImpl;

	
	public HelloUppercase(Hello helloImpl) {
		this.helloImpl = helloImpl;
	}

	public String sayHello(String name) {
		return helloImpl.sayHello(name).toUpperCase();
	}

	@Override
	public String sayHi(String name) {
		return helloImpl.sayHi(name).toUpperCase();
	}

	@Override
	public String sayThankYou(String name) {
		return helloImpl.sayThankYou(name).toUpperCase();
	}
}
