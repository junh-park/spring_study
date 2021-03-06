package com.jun.spring_practice.user.dao.learningtest;

import org.junit.*;

import com.jun.spring_practice.learningtest.Calculator;

import static org.hamcrest.Matchers.*;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.*;

public class CalcSumTest {
	Calculator calculator;
	String numFilePath;
	
	@Before
	public void setUp() {
		calculator = new Calculator();
		numFilePath= getClass().getClassLoader().getResource("numbers.txt").getPath();
	}
	
	@Test
	public void sumOfNumbers() throws IOException {
		assertThat(calculator.calcSum(numFilePath), is(10));
	}
	
	@Test
	public void multiplyOfNumbers() throws IOException {
		assertThat(calculator.calcMultiply(this.numFilePath), is(24));
	}
	
	@Test
	public void concatenateStrings() throws IOException {
		assertThat(calculator.concatenate(this.numFilePath), is("1234"));
	}
}
