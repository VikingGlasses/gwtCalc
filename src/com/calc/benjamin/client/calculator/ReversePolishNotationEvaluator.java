package com.calc.benjamin.client.calculator;

import java.util.EmptyStackException;
import java.util.Queue;
import java.util.Stack;

import com.calc.benjamin.client.calculator.exception.EvaluationException;
import com.calc.benjamin.client.calculator.exception.NoSuchOperator;

public class ReversePolishNotationEvaluator implements Evaluator<Queue<CalculatorToken>, Double> {

	@Override
	public Double eval(Queue<CalculatorToken> expression) throws EvaluationException {
		if (expression.isEmpty()) {
			return Double.NaN;
		}
		Stack<Double> operandStack = new Stack<>();
		try {
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
					throw new EvaluationException("CalculatorTokenType: '" + token.getTokenType().name() + "' not supported.");
				}
			}
			return operandStack.pop();
		} catch (NoSuchOperator e) {
			throw new EvaluationException("Operation: '" + e.getOperator() + "', not supported.");
		} catch (EmptyStackException e) {
			throw new EvaluationException("Bad Operand to Operation ratio.");
		}
	}

}
