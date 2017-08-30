package com.calc.benjamin.client.calculator;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import com.calc.benjamin.client.calculator.exception.ParseException;

public class ShuntingYardParser implements Parser<String[], Queue<CalculatorToken>> {

	@Override
	public Queue<CalculatorToken> parse(String[] expression) throws ParseException {
		Queue<CalculatorToken> queue = new LinkedList<>();
		Stack<CalculatorToken> operatorStack = new Stack<>();
		for (String token : expression) {
			if (!token.isEmpty()) {
				if (isNumber(token)) {
					queue.add(new CalculatorToken(CalculatorTokenType.OPERAND, token));
				} else if (Operator.isOperator(token)) {
					CalculatorToken newOp = new CalculatorToken(CalculatorTokenType.OPERATOR, token);
					while (!operatorStack.isEmpty()) {
						CalculatorToken topStackOperator = operatorStack.peek();
						if (topStackOperator.getTokenType() != CalculatorTokenType.OPERATOR) {
							break;
						}
						int topStackOpPrecedance = Operator.getOperation(topStackOperator.getValue()).getPrecedence();
						if (topStackOpPrecedance >= Operator.getOperation(newOp.getValue()).getPrecedence()) {
							queue.add(operatorStack.pop());
						} else {
							break;
						}
					}
					operatorStack.add(newOp);
				} else if (token.equals("(")) {
					operatorStack.push(new CalculatorToken(CalculatorTokenType.PARENTHESIS, token));
				} else if (token.equals(")")) {
					try {
						CalculatorToken peek = operatorStack.peek();
						while (peek.getTokenType() != CalculatorTokenType.PARENTHESIS) {
							queue.add(operatorStack.pop());
							peek = operatorStack.peek();
						}
						operatorStack.pop();
					} catch (EmptyStackException e) {
						throw new ParseException("Missmatched parenthesis");
					}
				} else {
					throw new ParseException("Unhandled token: '" + token + "'");
				}
			}
		}
		while (!operatorStack.isEmpty()) {
			if (operatorStack.peek().getTokenType() == CalculatorTokenType.PARENTHESIS) {
				operatorStack.pop();
				throw new ParseException("Missmatched parenthesis");
			}
			queue.add(operatorStack.pop());
		}
		return queue;
	}

	// TODO move somewhere else
	private boolean isNumber(String string) {
		try {
			Double.parseDouble(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
