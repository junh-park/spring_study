package com.jun.spring_practice.learningtest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

	public Integer calcSum(String filePath) throws IOException {
		BufferedReaderCallback callback = br -> {
			Integer sum = 0;
			String line = null;

			while ((line = br.readLine()) != null) {
				sum += Integer.valueOf(line);
			}

			return sum;
		};

		return fileReadTemplate(filePath, callback);
	}


	public int calcProd(String path) throws IOException {
		BufferedReaderCallback brc = new BufferedReaderCallback() {
			
			public Integer doSomethingWithReader(BufferedReader br) throws IOException {	
				Integer prod = 1;
				String line = null;
				
				while((line = br.readLine()) != null) {
					prod *= Integer.valueOf(line);
				}
				return prod;
			}
		};
		return fileReadTemplate(path, brc);
	}
	
	public Integer fileReadTemplate(String filePath, BufferedReaderCallback callback)
			throws IOException {
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(filePath));

			int ret = callback.doSomethingWithReader(br);

			return ret;

		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw e;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.getMessage();
				}
			}
		}
	}
}
