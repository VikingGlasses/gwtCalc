package com.calc.benjamin.client.shitty;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class MyParser implements ExpressionParser {

	@Override
	public ParsedExpression parse(String[] splitExpression) {
		List<Double> values = new ArrayList<>(2);
		List<String> operations = new ArrayList<>(1);
		boolean isNegative = false;
		for (String part : splitExpression) {
			if (!part.isEmpty()) {
				if (part.equals("-")) {
					isNegative = true;
				} else if (part.matches("[^0-9]")) {
					operations.add(part);
				} else {
					if (isNegative) {
						part = "-" + part;
						isNegative = false;
					}
					values.add(Double.parseDouble(part));
				}
			}
		}
		return new ParsedExpression(values, operations);
	}

}
