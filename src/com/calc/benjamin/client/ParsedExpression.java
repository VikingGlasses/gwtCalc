package com.calc.benjamin.client;

import java.util.List;

public class ParsedExpression {

	private double[] values;
	private String[] operators;
	
	public ParsedExpression() {
		values = new double[0];
		operators = new String[0];
	}
	
	public ParsedExpression(double[] values, String[] operators) {
		this.values = values;
		this.operators = operators;
	}

	public ParsedExpression(List<Double> values, List<String> operations) {
		operators = operations.toArray(new String[0]);
		setValues(values);
	}

	public double[] getValues() {
		return values;
	}

	public void setValues(double[] values) {
		this.values = values;
	}

	private void setValues(List<Double> values) {
		this.values = new double[values.size()];
		for (int i = 0; i < this.values.length; i++) {
			this.values[i] = values.get(i);
		}
	}

	public String[] getOperators() {
		return operators;
	}

	public void setOperators(String[] operators) {
		this.operators = operators;
	}
	
	public double getValue(int index) {
		return values[index];
	}
	
	public String getOperator(int index) {
		return operators[index];
	}

	public boolean operationsIsEmpty() {
		return operators.length == 0;
	}

	public boolean valuesIsEmpty() {
		return values.length == 0;
	}

}
