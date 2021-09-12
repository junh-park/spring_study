package com.jun.spring_practice.user.dao.learningtest;

import org.junit.Test;

import com.jun.spring_practice.learningtest.HelloTarget;
import com.jun.spring_practice.learningtest.HelloUppercase;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class HelloTargetTest {

	@Test
	public void simpleProxy() {
		HelloTarget helloTarget = new HelloTarget();
		assertThat(helloTarget.sayHello("Jun"), is("Hello Jun"));
		assertThat(helloTarget.sayHi("Jun"), is("Hi Jun"));
		assertThat(helloTarget.sayThankYou("Jun"), is("Thank You Jun"));
	}
	
	@Test
	public void helloUppercaseProxy() {
		HelloUppercase helloUppercase = new HelloUppercase(new HelloTarget());
		assertThat(helloUppercase.sayHello("Jun"), is("HELLO JUN"));
		assertThat(helloUppercase.sayHi("Jun"), is("HI JUN"));
		assertThat(helloUppercase.sayThankYou("Jun"), is("THANK YOU JUN"));
	}
}
