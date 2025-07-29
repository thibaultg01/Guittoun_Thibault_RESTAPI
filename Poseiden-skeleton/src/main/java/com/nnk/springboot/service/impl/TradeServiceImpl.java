package com.nnk.springboot.service.impl;

import org.springframework.stereotype.Service;

import com.nnk.springboot.model.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.TradeService;

import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;

    public TradeServiceImpl(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Override
    public List<Trade> findAll() {
        return tradeRepository.findAll();
    }

    @Override
    public Trade getByIdOrThrow(Integer id) {
        return tradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trade not found with id: " + id));
    }

    @Override
    public Trade save(Trade trade) {
        return tradeRepository.save(trade);
    }

    @Override
    public Trade update(Integer id, Trade trade) {
        if (!tradeRepository.existsById(id)) {
            throw new RuntimeException("Cannot update. Trade not found with id: " + id);
        }
        trade.setTradeId(id);
        return tradeRepository.save(trade);
    }

    @Override
    public void delete(Integer id) {
        if (!tradeRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete. Trade not found with id: " + id);
        }
        tradeRepository.deleteById(id);
    }
}