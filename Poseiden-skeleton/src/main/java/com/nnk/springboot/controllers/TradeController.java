package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.model.Trade;
import com.nnk.springboot.service.TradeService;

@Controller
@RequestMapping("/trade")
public class TradeController {

	private final TradeService tradeService;

	public TradeController(TradeService tradeService) {
		this.tradeService = tradeService;
	}

	@GetMapping("/list")
	public String listTrades(Model model) {
		model.addAttribute("trades", tradeService.findAll());
		return "trade/list";
	}

	@GetMapping("/add")
	public String showAddForm(Trade trade) {
		return "trade/add";
	}

	@PostMapping("/validate")
	public String validate(@ModelAttribute Trade trade) {
		tradeService.save(trade);
		return "redirect:/trade/list";
	}

	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
	    Trade trade = tradeService.getByIdOrThrow(id);
	    model.addAttribute("trade", trade);
	    return "trade/update";
	}

	@PostMapping("/update/{id}")
	public String updateTrade(@PathVariable("id") Integer id, @ModelAttribute Trade trade) {
		tradeService.update(id, trade);
		return "redirect:/trade/list";
	}

	@GetMapping("/delete/{id}")
	public String deleteTrade(@PathVariable("id") Integer id) {
		tradeService.delete(id);
		return "redirect:/trade/list";
	}
}