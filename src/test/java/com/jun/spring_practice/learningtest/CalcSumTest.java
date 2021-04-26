package com.jun.spring_practice.learningtest;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class CalcSumTest {
	private Calculator cal;
	private String numFilepath;
	
	@Before
	public void setUp() {
		this.cal = new Calculator();
		this.numFilepath = getClass().getResource("test_text.txt").getPath();
	}
	
	@Test
	public void sumOfNumbers() throws IOException {
		assertThat(cal.calcSum(this.numFilepath), is(10));
	}
	
	@Test
	public void productOfNumbers() throws IOException {
		assertThat(cal.calcProd(this.numFilepath), is(24));
	}
}
