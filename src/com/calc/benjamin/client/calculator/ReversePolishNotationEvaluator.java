package com.calc.benjamin.client.calculator;

import java.util.Queue;
import java.util.Stack;

import com.calc.benjamin.client.util.SimpleMath;
import com.google.gwt.core.shared.GWT;

public class ReversePolishNotationEvaluator implements Evaluator<Queue<CalculatorToken>, Double> {

	@Override
	public Double eval(Queue<CalculatorToken> expression) throws Exception {
		Stack<Double> operandStack = new Stack<>();
		while (!expression.isEmpty()) {
			CalculatorToken token = expression.poll();
			switch (token.getTokenType()) {
			case OPERAND:
				operandStack.add(Double.parseDouble(token.getValue()));
				break;
			case OPERATOR:
				Operator operator = Operator.getOperation(token.getValue());
				double val2 = operandStack.pop();
				double val1 = operandStack.pop();
				double result = SimpleMath.calc(operator, val1, val2);
				operandStack.add(result);
				break;
			default:
				// TODO throw exception?
				GWT.log("Token missmatch: " + token.toString());
				break;
			}
		}
		return operandStack.pop();
	}

}
