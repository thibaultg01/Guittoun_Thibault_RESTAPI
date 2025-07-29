package com.nnk.springboot.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.model.Trade;
import com.nnk.springboot.service.TradeService;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TradeControllerTest {

	@Mock
	private TradeService tradeService;

	@Mock
	private Model model;

	@InjectMocks
	private TradeController tradeController;

	private Trade trade;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		trade = new Trade();
		trade.setTradeId(1);
		trade.setAccount("TestAccount");
		trade.setType("TestType");
	}

	@Test
	void listTrades_shouldReturnListView() {
		when(tradeService.findAll()).thenReturn(Arrays.asList(trade));

		String viewName = tradeController.listTrades(model);

		verify(model, times(1)).addAttribute(eq("trades"), any());
		assertEquals("trade/list", viewName);
	}

	@Test
	void showAddForm_shouldReturnAddView() {
		String viewName = tradeController.showAddForm(trade);

		assertEquals("trade/add", viewName);
	}

	@Test
	void validate_shouldRedirectToList() {
		when(tradeService.save(any(Trade.class))).thenReturn(trade);

		String viewName = tradeController.validate(trade);

		assertEquals("redirect:/trade/list", viewName);
	}

	@Test
	void showUpdateForm_shouldReturnUpdateView() {
		when(tradeService.getByIdOrThrow(1)).thenReturn(trade);

		String viewName = tradeController.showUpdateForm(1, model);

		verify(model, times(1)).addAttribute("trade", trade);
		assertEquals("trade/update", viewName);
	}

	@Test
	void updateTrade_shouldRedirectToList() {
		when(tradeService.update(1, trade)).thenReturn(trade);

		String viewName = tradeController.updateTrade(1, trade);

		assertEquals("redirect:/trade/list", viewName);
	}

	@Test
	void deleteTrade_shouldRedirectToList() {
		doNothing().when(tradeService).delete(1);

		String viewName = tradeController.deleteTrade(1);

		assertEquals("redirect:/trade/list", viewName);
	}
}
