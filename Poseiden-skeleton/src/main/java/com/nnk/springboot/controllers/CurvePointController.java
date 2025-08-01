package com.nnk.springboot.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.model.CurvePoint;
import com.nnk.springboot.security.CustomUserDetails;
import com.nnk.springboot.service.CurvePointService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/curvePoint")
public class CurvePointController {

	private final CurvePointService curvePointService;

	public CurvePointController(CurvePointService curvePointService) {
		this.curvePointService = curvePointService;
	}

	@GetMapping("/list")
	public String listCurvePoints(Model model, @AuthenticationPrincipal CustomUserDetails user) {
		model.addAttribute("username", user.getUsername());
		model.addAttribute("curvePoints", curvePointService.findAll());
		return "curvePoint/list";
	}

	@GetMapping("/add")
	public String showAddForm(CurvePoint curvePoint) {
		return "curvePoint/add";
	}

	@PostMapping("/validate")
	public String validate(@ModelAttribute CurvePoint curvePoint) {
		curvePointService.save(curvePoint);
		return "redirect:/curvePoint/list";
	}

	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		CurvePoint curvePoint = curvePointService.getByIdOrThrow(id);
		model.addAttribute("curvePoint", curvePoint);
		return "curvePoint/update";
	}

	@PostMapping("/update/{id}")
	public String updateCurvePoint(@PathVariable("id") Integer id, @ModelAttribute CurvePoint curvePoint) {
		curvePointService.update(id, curvePoint);
		return "redirect:/curvePoint/list";
	}

	@GetMapping("/delete/{id}")
	public String deleteCurvePoint(@PathVariable("id") Integer id) {
		curvePointService.delete(id);
		return "redirect:/curvePoint/list";
	}
}