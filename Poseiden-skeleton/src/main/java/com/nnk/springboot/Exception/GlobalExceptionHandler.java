package com.nnk.springboot.Exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nnk.springboot.model.User;
import com.nnk.springboot.service.UserService;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public String handleResourceNotFound(ResourceNotFoundException ex, Model model) {
		model.addAttribute("errorMessage", ex.getMessage());
		return "/404";
	}

	@ExceptionHandler(InvalidValidatePasswordException.class)
	public String handlePasswordInvalid(InvalidValidatePasswordException ex, Model model) {
		model.addAttribute("passwordError", ex.getMessage());
		model.addAttribute("user", ex.user());
		return ex.getRedirectUrl();
	}
}