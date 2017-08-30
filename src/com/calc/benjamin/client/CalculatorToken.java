package com.calc.benjamin.client;

public class CalculatorToken {
	
	private CalculatorTokenType tokenType;
	private String value;

	public CalculatorToken(CalculatorTokenType type) {
		this.tokenType = type;
	}

	public CalculatorToken(CalculatorTokenType type, String value) {
		this.tokenType = type;
		this.value = value;
	}

	public CalculatorTokenType getTokenType() {
		return tokenType;
	}
	
	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return tokenType.name();
	}

}
