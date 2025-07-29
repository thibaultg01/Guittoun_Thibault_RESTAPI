package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.model.BidList;
import com.nnk.springboot.service.BidListService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/bidList")
public class BidListController {

	private final BidListService bidListService;

	public BidListController(BidListService bidListService) {
		this.bidListService = bidListService;
	}

	@GetMapping("/list")
	public String listBidLists(Model model) {
		model.addAttribute("bidLists", bidListService.findAll());
		return "bidList/list";
	}

	@GetMapping("/add")
	public String showAddForm(BidList bidList) {
		return "bidList/add";
	}

	@PostMapping("/validate")
	public String validate(@ModelAttribute BidList bidList) {
		bidListService.save(bidList);
		return "redirect:/bidList/list";
	}

	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		BidList bidList = bidListService.getByIdOrThrow(id);
		model.addAttribute("bidList", bidList);
		return "bidList/update";
	}

	@PostMapping("/update/{id}")
	public String updateBidList(@PathVariable("id") Integer id, @ModelAttribute BidList bidList) {
		bidListService.update(id, bidList);
		return "redirect:/bidList/list";
	}

	@GetMapping("/delete/{id}")
	public String deleteBidList(@PathVariable("id") Integer id) {
		bidListService.delete(id);
		return "redirect:/bidList/list";
	}
}