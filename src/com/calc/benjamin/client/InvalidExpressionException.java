package com.calc.benjamin.client;

public class InvalidExpressionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8856309894279098369L;
	private String reason;

	public InvalidExpressionException(String reason) {
		this.reason = reason;
	}
	
	public String getReason() {
		return reason;
	}
}
