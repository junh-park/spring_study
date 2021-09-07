package com.jun.spring_practice.entity.domain;

public enum Level {
	BASIC(1), SILVER(2), GOLD(3);
	
	private final int value;
	
	Level(int i) {
		this.value = i;
	}
	
	public int intValue() {
		return value;
	}
	
	public static Level valueOf(int value) {
		switch(value) {
		case 1: return BASIC;
		case 2: return SILVER;
		case 3: return GOLD;
		default: throw new AssertionError("Unkown value: " + value);
		}
	}
	
	
}
