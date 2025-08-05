package com.nnk.springboot.Exception;

import com.nnk.springboot.model.User;

public class InvalidValidatePasswordException extends RuntimeException {
	private final String redirectUrl;
	private final User user;

	public InvalidValidatePasswordException(String message, String redirectUrl, User user) {
		super(message);
		this.redirectUrl = redirectUrl;
		this.user = user;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public User user() {
		return user;
	}
}
