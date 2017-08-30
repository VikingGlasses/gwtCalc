package com.calc.benjamin.client.calculator.exception;

public class NoSuchOperator extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8136371194389988816L;
	private String operator;

	public NoSuchOperator(String operator) {
		this.operator = operator;
	}
	
	public String getOperator() {
		return operator;
	}

}
