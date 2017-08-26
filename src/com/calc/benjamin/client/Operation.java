package com.calc.benjamin.client;

public enum Operation {
	
	PLUS(2, Associativity.LEFT),
	MINUS(2, Associativity.LEFT),
	MODULU(3, Associativity.LEFT),
	DIVISION(3, Associativity.LEFT),
	MULTIPLICATION(3, Associativity.LEFT);
	
	private int precedence;
	private Associativity ass;

	private Operation(int precedence, Associativity ass) {
		this.precedence = precedence;
		this.ass = ass;
	}

	public int getPrecedence() {
		return precedence;
	}

	public Associativity getAssociativity() {
		return ass;
	}
	
	public static Operation getOperation(String symbol) {
		Operation op;
		switch (symbol) {
		case "+":
			op = PLUS;
			break;
		case "-":
			op = MINUS;
			break;
		case "%":
			op = MODULU;
			break;
		case "*":
			op = MULTIPLICATION;
			break;
		case "/":
			op = DIVISION;
			break;
		default:
			throw new IllegalArgumentException("Missing case for operation: '" + symbol + "'");			
		}
		return op;
	}

}
