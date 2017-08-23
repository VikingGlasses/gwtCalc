package com.calc.benjamin.client;

public class SimpleMath {

	public static double calc(String operator, double val1, double val2) throws NoSuchOperator {
		double result;
		switch (operator) {
		case "/":
			result = divide(val1, val2);
			break;
		case "+":
			result = add(val1, val2);
			break;
		case "*":
			result = multiply(val1, val2);
			break;
		case "-":
			result = subtract(val1, val2);
			break;
		case "%":
			result = mod(val1, val2);
			break;
		default:
			throw new NoSuchOperator("Missing operation for '" + operator + "'");
		}
		return result;
	}

	public static double mod(double val1, double val2) {
		return val1 % val2;
	}

	public static double subtract(double val1, double val2) {
		return val1 - val2;
	}

	public static double multiply(double val1, double val2) {
		return val1 * val2;
	}

	public static double add(double val1, double val2) {
		return val1 + val2;
	}

	public static double divide(double val1, double val2) {
		return val1 / val2;
	}

}