package com.calc.benjamin.client;

public class Operator extends CalculatorToken {
	
	private Operation op;

	public Operator(CalculatorTokenType type, String token) {
		super(type);
		op = Operation.getOperation(token);
	}

	public int getPrecedance() {
		return op.getPrecedence();
	}
	
	@Override
	public String toString() {
		return op.toString();
	}

}
