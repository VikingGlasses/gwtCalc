package com.calc.benjamin.client.calculator;

public final class CalculatorToken {
	
	private CalculatorTokenType tokenType;
	private String value;

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
		return value;
	}

}
