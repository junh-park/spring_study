package com.jun.spring_practice.user.dao.learningtest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.lang.reflect.Proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import com.jun.spring_practice.learningtest.proxy.Hello;
import com.jun.spring_practice.learningtest.proxy.HelloTarget;
import com.jun.spring_practice.learningtest.proxy.HelloUppercase;
import com.jun.spring_practice.learningtest.proxy.UppercaseHandler;

public class DynamicProxyTest {

	@Test
	public void simpleProxy() {
		HelloTarget helloTarget = new HelloTarget();
		
		lowercaseTest(helloTarget);
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
		uppercaseTest(proxiedHello);
	}
	
	@Test
	public void proxyFactoryBean() {
		ProxyFactoryBean pfBean = new ProxyFactoryBean();
		pfBean.setTarget(new HelloTarget());
		pfBean.addAdvice(new UppercaseAdvice());
		
		Hello proxiedHello = (Hello) pfBean.getObject();
		uppercaseTest(proxiedHello);
		
	}
	
	static class UppercaseAdvice implements MethodInterceptor{
		public Object invoke(MethodInvocation invocation) throws Throwable {
			String ret = (String) invocation.proceed();
			return ret.toUpperCase();		}
	}

	@Test
	public void pointcutAdvisor() {
		ProxyFactoryBean pfBean = new ProxyFactoryBean();
		pfBean.setTarget(new HelloTarget());
		
		NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
		pointcut.setMappedName("sayH*");
		pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));
		
		Hello proxiedHello = (Hello) pfBean.getObject();
		assertThat(proxiedHello.sayHello("Jun"), is("HELLO JUN"));
		assertThat(proxiedHello.sayHi("Jun"), is("HI JUN"));
		assertThat(proxiedHello.sayThankYou("Jun"), is("Thank You Jun"));	}
	
	@Test
	public void classNamePointcutAdvisor() {
		NameMatchMethodPointcut classMethodPointcut = new NameMatchMethodPointcut() {
			public ClassFilter getClassFilter() {
				return new ClassFilter() {
					public boolean matches(Class<?> clazz) {
						return clazz.getSimpleName().startsWith("HelloT");
					}
				};
			}
		};
		classMethodPointcut.setMappedName("sayH*");
		
		checkAdviced(new HelloTarget(), classMethodPointcut, true);
		
		class HelloWorld extends HelloTarget {};
		checkAdviced(new HelloWorld(), classMethodPointcut, false);
		
		class HelloJun extends HelloTarget {};
		checkAdviced(new HelloJun(), classMethodPointcut, false);
	}
	
	private void checkAdviced(Object target, Pointcut pointcut, boolean adviced) {
		ProxyFactoryBean pfBean = new ProxyFactoryBean();
		pfBean.setTarget(target);
		pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));
		Hello proxiedHello = (Hello) pfBean.getObject();
		
		if(adviced) {
			assertThat(proxiedHello.sayHello("Jun"), is("HELLO JUN"));
			assertThat(proxiedHello.sayHi("Jun"), is("HI JUN"));
			assertThat(proxiedHello.sayThankYou("Jun"), is("Thank You Jun"));
		} else {
			lowercaseTest(proxiedHello);
		}
	}

	private void uppercaseTest(Hello proxiedHello) {
		assertThat(proxiedHello.sayHello("Jun"), is("HELLO JUN"));
		assertThat(proxiedHello.sayHi("Jun"), is("HI JUN"));
		assertThat(proxiedHello.sayThankYou("Jun"), is("THANK YOU JUN"));
	}
	
	private void lowercaseTest(Hello proxiedHello) {
		assertThat(proxiedHello.sayHello("Jun"), is("Hello Jun"));
		assertThat(proxiedHello.sayHi("Jun"), is("Hi Jun"));
		assertThat(proxiedHello.sayThankYou("Jun"), is("Thank You Jun"));
	}
	
}
