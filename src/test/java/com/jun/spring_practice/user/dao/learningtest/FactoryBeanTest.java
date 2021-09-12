package com.jun.spring_practice.user.dao.learningtest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isA;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jun.spring_practice.learningtest.factorybean.message.Message;
import com.jun.spring_practice.learningtest.factorybean.message.MessageFactoryBean;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:FactoryBeanTest-context.xml")
public class FactoryBeanTest {
	@Autowired
	ApplicationContext context;
	
	@Test
	public void getMessageFactoryBean() {
		Object message = context.getBean("message");
		assertThat(message, isA(Message.class));
		assertThat(((Message) message).getText(), is("Factory Bean"));
	}
	
	@Test
	public void getFactoryBean() {
		Object factory = context.getBean("&message");
		assertThat(factory, isA(MessageFactoryBean.class));
	}
}
