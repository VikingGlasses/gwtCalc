package com.calc.benjamin.client.calculator;

import com.calc.benjamin.client.calculator.exception.EvaluationException;

public interface Evaluator<I, O> {
	
	O eval(I expression) throws EvaluationException;

}
