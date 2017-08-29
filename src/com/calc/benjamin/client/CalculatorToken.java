package com.calc.benjamin.client;

public abstract class CalculatorToken {
	
	private CalculatorTokenType tokenType;

	public CalculatorToken(CalculatorTokenType type) {
		this.tokenType = type;
	}

	public CalculatorTokenType getTokenType() {
		return tokenType;
	}

	public void setTokenType(CalculatorTokenType tokenType) {
		this.tokenType = tokenType;
	}
	
	@Override
	public String toString() {
		return tokenType.name();
	}

}
