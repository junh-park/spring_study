package com.jun.spring_practice.learningtest.factorybean.message;

public class Message {
	String text;

	private Message(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
	
	public static Message newMessage(String text) {
		return new Message(text);
	}
}
