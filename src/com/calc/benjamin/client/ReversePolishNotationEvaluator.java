package com.calc.benjamin.client;

import java.util.Queue;
import java.util.Stack;

import com.calc.benjamin.client.util.SimpleMath;
import com.google.gwt.core.shared.GWT;

public class ReversePolishNotationEvaluator implements Evaluator<Queue<CalculatorToken>, Double> {

	@Override
	public Double eval(Queue<CalculatorToken> expression) throws Exception {
		Stack<Operand> operandStack = new Stack<>();
		while (!expression.isEmpty()) {
			CalculatorToken token = expression.poll();
			if (token.getTokenType() == CalculatorTokenType.OPERAND) {
				operandStack.add((Operand) token);
			} else if (token.getTokenType() == CalculatorTokenType.OPERATOR){
				Operator operator = (Operator) token;
				Operand val2 = operandStack.pop();
				Operand val1 = operandStack.pop();
				double result = SimpleMath.calc(operator.getOperation(), val1.getValue(), val2.getValue());
				operandStack.add(new Operand(CalculatorTokenType.OPERAND, result));
			} else {
				// TODO throw exception?
				GWT.log("Token missmatch: " + token.toString());
			}
		}
		return operandStack.pop().getValue();
	}

}
