package com.jun.spring_practice.user.dao.learningtest;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import com.jun.spring_practice.learningtest.pointcut.Bean;
import com.jun.spring_practice.learningtest.pointcut.Target;

public class PointcutExpressionTest {
	@Test
	public void methodSignaturePointcut() throws NoSuchMethodException, SecurityException {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression("execution(public int " 
				+ "com.jun.spring_practice.learningtest.pointcut.Target.minus(int,int)"
				+ "throws java.lang.RuntimeException)");
		
		assertThat(pointcut.getClassFilter().matches(Target.class) && pointcut.getMethodMatcher().matches(Target.class.getMethod("minus", int.class, int.class), null), is(true));
		
		assertThat(pointcut.getClassFilter().matches(Target.class) && pointcut.getMethodMatcher().matches(Target.class.getMethod("plus", int.class, int.class),null), is(false));
		
		assertThat(pointcut.getClassFilter().matches(Target.class) && pointcut.getMethodMatcher().matches(Target.class.getMethod("method"), null), is(false));
		
	}
	
	@Test
	public void pointcut() throws NoSuchMethodException, SecurityException {
		targetClassPointcutMatches("execution(* *(..))", true, true, true, true, true, true);
		targetClassPointcutMatches("execution(* hello(..))", true, true, false, false, false, false);
		targetClassPointcutMatches("execution(* hello(String))", false, true, false, false, false, false);
		targetClassPointcutMatches("execution(* hello())", true, false, false, false, false, false);
		targetClassPointcutMatches("execution(* meth*(..))", false, false, false, false, true, true);
		targetClassPointcutMatches("execution(* *(int,int))", false, false, true, true, false, false);
		targetClassPointcutMatches("execution(* *())", true, false, false, false, true, true);
		targetClassPointcutMatches("execution(* com.jun.spring_practice.learningtest.pointcut.Target.*(..))",
				true, true, true, true, true, false);
		targetClassPointcutMatches("execution(* com.jun.spring_practice.learningtest.pointcut.*.*(..))", 
				true, true, true, true, true, true);
		targetClassPointcutMatches("execution(* com.jun.spring_practice.learningtest.pointcut..*.*(..))", 
				true, true, true, true, true, true);
		targetClassPointcutMatches("execution(* com.jun.spring_practice..*.*(..))", 
				true, true, true, true, true, true);
		targetClassPointcutMatches("execution(* jun..*.*(..))", false, false, false, false, false, false);
		targetClassPointcutMatches("execution(* *..Target.*(..))", true, true, true, true, true, false);
		targetClassPointcutMatches("execution(* *..Tar*.*(..))", true, true, true, true, true, false);
		targetClassPointcutMatches("execution(* *..*get.*(..))", true, true, true, true, true, false);
		targetClassPointcutMatches("execution(* *..B*.*(..))", false, false, false, false, false, true);
		targetClassPointcutMatches("execution(* *..TargetInterface.*(..))", true, true, true, true, false, false);
		targetClassPointcutMatches("execution(* *(..) throws Runtime*)", false, false, false, true, false, true);
		targetClassPointcutMatches("execution(int *(..))", false, false, true, true, false, false);
		targetClassPointcutMatches("execution(void *(..))", true, true, false, false, true, true);

	}
	
	 
	public void pointcutMatches(String expression, Boolean expected, Class<?> clazz, String methodName, Class<?>... args) throws NoSuchMethodException, SecurityException {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(expression);
		
		assertThat(pointcut.getClassFilter().matches(clazz) && pointcut.getMethodMatcher().matches(clazz.getMethod(methodName, args), null), is(expected));
	}
	
	public void targetClassPointcutMatches(String expression, boolean... expected) throws NoSuchMethodException, SecurityException {
		pointcutMatches(expression, expected[0], Target.class, "hello");
		pointcutMatches(expression, expected[1], Target.class, "hello", String.class);
		pointcutMatches(expression, expected[2], Target.class, "plus", int.class, int.class);
		pointcutMatches(expression, expected[3], Target.class, "minus", int.class, int.class);
		pointcutMatches(expression, expected[4], Target.class, "method");
		pointcutMatches(expression, expected[5], Bean.class, "method");
	}
	
}
