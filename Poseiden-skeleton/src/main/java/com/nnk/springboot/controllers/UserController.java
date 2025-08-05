package com.nnk.springboot.controllers;

import com.nnk.springboot.model.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.security.CustomUserDetails;
import com.nnk.springboot.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/list")
	public String listUsers(Model model, @AuthenticationPrincipal CustomUserDetails user) {
		model.addAttribute("username", user.getUsername());

		model.addAttribute("users", userService.findAll());
		return "user/list";
	}

	@GetMapping("/add")
	public String showAddForm(Model model) {
		model.addAttribute("user", new User());
		return "user/add";
	}

	@PostMapping("/validate")
	public String validate(@ModelAttribute User user, BindingResult result,Model model) {
		if (result.hasErrors()) {
	        return "user/add";
	    }
		userService.save(user);
		return "redirect:/user/list";
	}

	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		User existingUser = userService.getByIdOrThrow(id);
		model.addAttribute("user", existingUser);
		return "user/update";
	}

	@PostMapping("/update/{id}")
	public String updateUser(@PathVariable("id") Integer id, @ModelAttribute User user , BindingResult result) {
		if (result.hasErrors()) {
	        return "user/update/"+id;
	    }
		userService.update(id, user);
		return "redirect:/user/list";
	}

	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") Integer id) {
		userService.delete(id);
		return "redirect:/user/list";
	}
}