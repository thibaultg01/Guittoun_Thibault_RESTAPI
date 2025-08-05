package com.nnk.springboot.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nnk.springboot.Exception.InvalidValidatePasswordException;
import com.nnk.springboot.Exception.ResourceNotFoundException;
import com.nnk.springboot.model.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = new BCryptPasswordEncoder();
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User getByIdOrThrow(Integer id) {
		return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
	}

	@Override
	public User save(User user) {
		validatePassword(user.getPassword(), "user/add", user);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		if (user.getRole() == null) {
			user.setRole("USER");
		}
		return userRepository.save(user);
	}

	@Override
	public User update(Integer id, User user) {
		if (!userRepository.existsById(id)) {
			throw new ResourceNotFoundException("Cannot update. User not found with id: " + id);
		}
		validatePassword(user.getPassword(), "user/update", user);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setId(id);
		return userRepository.save(user);
	}

	@Override
	public void delete(Integer id) {
		if (!userRepository.existsById(id)) {
			throw new ResourceNotFoundException("Cannot delete. User not found with id: " + id);
		}
		userRepository.deleteById(id);
	}

	private void validatePassword(String password, String redirectUrl, User user) {
		if (password == null || password.length() < 8 || !password.matches(".*[A-Z].*") || !password.matches(".*\\d.*")
				|| !password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
			throw new InvalidValidatePasswordException(
					"Password must be at least 8 characters long, include a capital letter, a number, and a special character.",
					redirectUrl, user);
		}
	}
}