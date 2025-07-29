package com.nnk.springboot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.nnk.springboot.model.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.impl.TradeServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TradeServiceImplTest {

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeServiceImpl tradeService;

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
    void findAll_shouldReturnList() {
        when(tradeRepository.findAll()).thenReturn(Arrays.asList(trade));

        List<Trade> trades = tradeService.findAll();

        assertNotNull(trades);
        assertEquals(1, trades.size());
        verify(tradeRepository, times(1)).findAll();
    }

    @Test
    void getByIdOrThrow_shouldReturnTrade() {
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

        Trade result = tradeService.getByIdOrThrow(1);

        assertNotNull(result);
        assertEquals("TestAccount", result.getAccount());
        verify(tradeRepository, times(1)).findById(1);
    }

    @Test
    void getByIdOrThrow_shouldThrowException_whenNotFound() {
        when(tradeRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> tradeService.getByIdOrThrow(1));

        assertEquals("Trade not found with id: 1", exception.getMessage());
    }

    @Test
    void save_shouldReturnSavedTrade() {
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

        Trade saved = tradeService.save(trade);

        assertNotNull(saved);
        assertEquals("TestAccount", saved.getAccount());
        verify(tradeRepository, times(1)).save(trade);
    }

    @Test
    void update_shouldUpdateTrade() {
        when(tradeRepository.existsById(1)).thenReturn(true);
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

        Trade updated = tradeService.update(1, trade);

        assertNotNull(updated);
        verify(tradeRepository, times(1)).save(trade);
    }

    @Test
    void update_shouldThrowException_whenTradeNotFound() {
        when(tradeRepository.existsById(1)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> tradeService.update(1, trade));

        assertEquals("Cannot update. Trade not found with id: 1", exception.getMessage());
    }

    @Test
    void delete_shouldDeleteTrade() {
        when(tradeRepository.existsById(1)).thenReturn(true);
        doNothing().when(tradeRepository).deleteById(1);

        tradeService.delete(1);

        verify(tradeRepository, times(1)).deleteById(1);
    }

    @Test
    void delete_shouldThrowException_whenTradeNotFound() {
        when(tradeRepository.existsById(1)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> tradeService.delete(1));

        assertEquals("Cannot delete. Trade not found with id: 1", exception.getMessage());
    }
}

