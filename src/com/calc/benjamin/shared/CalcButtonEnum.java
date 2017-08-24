package com.calc.benjamin.shared;

import com.google.gwt.event.dom.client.KeyCodes;

public enum CalcButtonEnum {
	ONE("1", "numericButton"),
	TWO("2", "numericButton"), 
	THREE("3", "numericButton"),
	FOUR("4", "numericButton"),
	FIVE("5", "numericButton"),
	SIX("6", "numericButton"),
	SEVEN("7", "numericButton"),
	EIGHT("8", "numericButton"),
	NINE("9", "numericButton"),
	ZERO("0", "numericButton"),
	PLUS("+", "operatorButton"), 
	MINUS("-", "operatorButton"),
	DIVITION("/", "operatorButton"), 
	MULTIPLICATION("*", "operatorButton"),
	MODULUS("%", "operatorButton"),
	POINT(".", "operatorButton"),
	SIGN("+/-", "operatorButton"),
	EQUALS("=", "operatorButton"),
	ENTER("\u23ce", "enterButton"),
	EMPTY("\u0000", "numericButton"),
	BACKSPACE("\u232b", "backspaceButton");
	
	private String symbol;
	private String style;

	private CalcButtonEnum(String symbol, String Style) {
		this.setSymbol(symbol);
		setStyle(Style);
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
	
	public static CalcButtonEnum[] getOperations() {
		return new CalcButtonEnum[]{PLUS, DIVITION, MULTIPLICATION, MODULUS};
	}

	public static CalcButtonEnum getE(int nativeKeyCode) {
		switch (nativeKeyCode) {
			case KeyCodes.KEY_ENTER:
				return ENTER;
			case KeyCodes.KEY_BACKSPACE:
				return BACKSPACE;
			case KeyCodes.KEY_ONE:
			case KeyCodes.KEY_NUM_ONE:
				return ONE;
			case KeyCodes.KEY_TWO:
				return TWO;
			case KeyCodes.KEY_THREE:
				return THREE;
			case KeyCodes.KEY_FOUR:
				return FOUR;
			case KeyCodes.KEY_SIX:
				return SIX;
			case KeyCodes.KEY_SEVEN:
				return SEVEN;
			case KeyCodes.KEY_EIGHT:
				return EIGHT;
			case KeyCodes.KEY_NINE:
				return NINE;
			case KeyCodes.KEY_ZERO:
				return ZERO;
			case KeyCodes.KEY_NUM_PLUS:
				return PLUS;
			case KeyCodes.KEY_NUM_MINUS:
				return MINUS;
			case KeyCodes.KEY_NUM_DIVISION:
				return DIVITION;
			case KeyCodes.KEY_NUM_MULTIPLY:
				return MULTIPLICATION;
			case KeyCodes.KEY_NUM_PERIOD:
				return POINT;
			default:
				return null;
		}
	}
}
