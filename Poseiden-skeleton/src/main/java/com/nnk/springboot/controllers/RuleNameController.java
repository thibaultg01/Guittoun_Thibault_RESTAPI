package com.nnk.springboot.controllers;

import com.nnk.springboot.model.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.security.CustomUserDetails;
import com.nnk.springboot.service.RuleNameService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ruleName")
public class RuleNameController {
	private final RuleNameService ruleNameService;

	public RuleNameController(RuleNameService ruleNameService) {
		this.ruleNameService = ruleNameService;
	}

	@GetMapping("/list")
	public String listRuleNames(Model model, @AuthenticationPrincipal CustomUserDetails user) {
		model.addAttribute("username", user.getUsername());
		List<RuleName> ruleNames = ruleNameService.findAll();
		model.addAttribute("ruleNames", ruleNames);
		return "ruleName/list";
	}

	@GetMapping("/add")
	public String showAddForm(RuleName ruleName) {
		return "ruleName/add";
	}

	@PostMapping("/validate")
	public String validateRuleName(@ModelAttribute RuleName ruleName, Model model) {
		ruleNameService.save(ruleName);
		model.addAttribute("ruleNames", ruleNameService.findAll());
		return "redirect:/ruleName/list";
	}

	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		RuleName ruleName = ruleNameService.getByIdOrThrow(id);
		model.addAttribute("ruleName", ruleName);
		return "ruleName/update";
	}

	@PostMapping("/update/{id}")
	public String updateRuleName(@PathVariable("id") Integer id, @ModelAttribute RuleName ruleName) {
		ruleName.setId(id);
		ruleNameService.save(ruleName);
		return "redirect:/ruleName/list";
	}

	@GetMapping("/delete/{id}")
	public String deleteRuleName(@PathVariable("id") Integer id) {
		ruleNameService.deleteById(id);
		return "redirect:/ruleName/list";
	}
}