package com.calc.benjamin.client.calculator;

import com.calc.benjamin.client.calculator.exception.ParseException;

public interface Parser<I, O> {
	
	O parse(I expression) throws ParseException;

}
