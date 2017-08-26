package com.calc.benjamin.client.shitty;

import com.calc.benjamin.client.exception.InvalidExpressionException;
import com.calc.benjamin.client.exception.MissingValueException;
import com.calc.benjamin.client.exception.NoSuchOperator;
import com.calc.benjamin.client.util.SimpleMath;

public class SimpleEvaluator implements Evaluator {
	
	private final String regex; 
	private final ExpressionParser parser;
	
	public SimpleEvaluator() {
		String operators = "[/*+%\\-]";
		regex = "(?=" + operators + ")";
		parser = new MyParser();
	}
	
	@Override
	public double eval(String expression) throws Throwable {
		try {
			// split expression
			String[] parts = expression.split(regex);
			// parse expression
			final ParsedExpression pe = parser.parse(parts);
			if (pe.valuesIsEmpty()) {
				throw new MissingValueException(); 
			} else if (pe.getValues().length < 2) {
				return pe.getValue(0);
			} else if (pe.operationsIsEmpty() && pe.getValue(1) < 0) {
				pe.setOperators(new String[]{"+"});
			}
			// calculate result
			double result = SimpleMath.calc(pe.getOperator(0), pe.getValue(0), pe.getValue(1));
			return result;
		} catch (NoSuchOperator e) {
			throw new InvalidExpressionException(e.getReason());
		} catch (NumberFormatException e) {
			throw new InvalidExpressionException("parsing failed");
		} catch (Exception e) {
			if (e instanceof InvalidExpressionException || e instanceof MissingValueException) {
				throw e;
			}
			throw new InvalidExpressionException("Uknown");
		}
	}

}
