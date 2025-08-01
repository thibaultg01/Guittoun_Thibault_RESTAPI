package com.nnk.springboot.Exception;

public class InvalidValidatePasswordException extends RuntimeException {
	private final String redirectUrl;

	public InvalidValidatePasswordException(String message, String redirectUrl) {
		super(message);
		this.redirectUrl = redirectUrl;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}
}
