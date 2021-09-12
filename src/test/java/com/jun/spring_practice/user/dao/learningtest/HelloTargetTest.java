package com.jun.spring_practice.user.dao.learningtest;

import org.junit.Test;

import com.jun.spring_practice.learningtest.proxy.Hello;
import com.jun.spring_practice.learningtest.proxy.HelloTarget;
import com.jun.spring_practice.learningtest.proxy.HelloUppercase;
import com.jun.spring_practice.learningtest.proxy.UppercaseHandler;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.lang.reflect.Proxy;

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

	@Test
	public void proxiedHelloTarget() {
		Hello proxiedHello = (Hello) Proxy.newProxyInstance(
				getClass().getClassLoader(), new Class[] { Hello.class }, new UppercaseHandler(new HelloTarget()));
		assertThat(proxiedHello.sayHello("Jun"), is("HELLO JUN"));
		assertThat(proxiedHello.sayHi("Jun"), is("HI JUN"));
		assertThat(proxiedHello.sayThankYou("Jun"), is("THANK YOU JUN"));
	}
}
