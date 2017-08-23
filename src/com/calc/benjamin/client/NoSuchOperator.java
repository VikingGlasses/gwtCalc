package com.calc.benjamin.client;

public class NoSuchOperator extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8136371194389988816L;
	private String reason;

	public NoSuchOperator(String reason) {
		this.reason = reason;
	}
	
	public String getReason() {
		return reason;
	}

}
