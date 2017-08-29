package com.calc.benjamin.client;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import com.calc.benjamin.client.exception.ParenthesisMissMatchException;
import com.google.gwt.core.shared.GWT;

public class ShuntingYardParser implements Parser<String[], Queue<CalculatorToken>> {

	@Override
	public Queue<CalculatorToken> parse(String[] expression) throws ParenthesisMissMatchException {
		Queue<CalculatorToken> queue = new LinkedList<>();
		Stack<CalculatorToken> operatorStack = new Stack<>();
		for (String token : expression) {
			if (!token.isEmpty()) {
				if (isNumber(token)) {
					queue.add(new Operand(CalculatorTokenType.OPERAND, token));
				} else if (isOperator(token)) {
					Operator newOp = new Operator(CalculatorTokenType.OPERATOR, token);
					while (!operatorStack.isEmpty()) {
						CalculatorToken calcToken = operatorStack.peek();
						if (calcToken.getTokenType() == CalculatorTokenType.OPERATOR) {
							Operator op = (Operator) calcToken;
							if (op.getPrecedance() >= newOp.getPrecedance()) {
								queue.add(operatorStack.pop());
							} else {
								break;
							}
						} else if (calcToken.getTokenType() == CalculatorTokenType.PARENTHESIS) {
							break;
						}
					}
					operatorStack.add(newOp);
				} else if (token.equals("(")) {
					operatorStack.push(new Parenthesis(CalculatorTokenType.PARENTHESIS, Associativity.LEFT));
				} else if (token.equals(")")) {
					try {
						CalculatorToken peek = operatorStack.peek();
						while (peek.getTokenType() != CalculatorTokenType.PARENTHESIS) {
							queue.add(operatorStack.pop());
							peek = operatorStack.peek();
						}
						// TODO check parenthesis associativity
						operatorStack.pop();
					} catch (EmptyStackException e) {
						throw new ParenthesisMissMatchException();
					}
				} else {
					GWT.log("Unused token: '" + token + "'");
				}
			}
		}
		while (!operatorStack.isEmpty()) {
			if (operatorStack.peek().getTokenType() == CalculatorTokenType.PARENTHESIS) {
				operatorStack.pop();
//				throw new ParenthesisMissMatchException();
			}
			queue.add(operatorStack.pop());
		}
		return queue;
	}

	private boolean isOperator(String string) {
		if (string.matches("([+/*%\\-])")) {
			return true;
		}
		return false;
	}

	private boolean isNumber(String string) {
		try {
			Double.parseDouble(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}