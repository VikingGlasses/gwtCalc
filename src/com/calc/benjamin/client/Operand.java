package com.calc.benjamin.client;

public class Operand extends CalculatorToken {
	
	private double value;

	public Operand(CalculatorTokenType type, double value) {
		super(type);
		this.value = value;
	}

	public Operand(CalculatorTokenType type, String string) {
		super(type);
		this.value = Double.parseDouble(string);
	}

	public double getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return Double.toString(value);
	}

}
