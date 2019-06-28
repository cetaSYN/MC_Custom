package com.mc_custom.core.exceptions;

public abstract class DetailedException extends Exception {

	private String message;

	public DetailedException() {
	}

	public DetailedException(String message) {
		this.message = message;
	}

	public String getAdditionalDetails() {
		return message;
	}

	public abstract String getMessage(String command);
}
