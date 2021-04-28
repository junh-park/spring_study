package com.jun.spring_practice.learningtest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.jun.spring_practice.learningtest.template.LineCallback;

public class Calculator {

	public int calcSum(String path) throws IOException {
		BufferedReaderCallback sumCallback = new BufferedReaderCallback() {
			public Integer doSomethingWithReader(BufferedReader br) throws IOException {
				Integer sum = 0;
				String line = null;
				while ((line = br.readLine()) != null ) {
					sum += Integer.valueOf(line);
				}
				return sum;
			}
		};
		return fileReadTemplate(path, sumCallback);
	}
	
	public Integer calcMultiply(String numFilePath) throws IOException {
			LineCallback<Integer> lineCallback = new LineCallback<Integer>() {
				public Integer doSomethingWithLine(String line, Integer value) {
					return value * Integer.valueOf(line);
			};
		};
			return lineReadTemplate(numFilePath, lineCallback, 1);
	}

	private <T> T lineReadTemplate(String path, LineCallback<T> callback, T initVal) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path));
			T res = initVal;
			String line = null;
			while ((line = br.readLine()) != null) {
				res = callback.doSomethingWithLine(line, res);
			}
			return res;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
		finally {
			if (br != null) try { br.close(); } catch (IOException e) {System.out.println(e.getMessage());}
		}
	}
	
	private int fileReadTemplate(String path, BufferedReaderCallback callback) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path));
			int ret = callback.doSomethingWithReader(br);
			return ret;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
		finally {
			if (br != null) try { br.close(); } catch (IOException e) {System.out.println(e.getMessage());}
		}
	}
}
