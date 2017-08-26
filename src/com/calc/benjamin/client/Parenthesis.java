package com.calc.benjamin.client;

public class Parenthesis extends CalculatorToken {

	private Associativity associativity;

	public Parenthesis(CalculatorTokenType type, Associativity associativity) {
		super(type);
		this.associativity = associativity;
	}

	public Associativity getAssociativity() {
		return associativity;
	}

}
