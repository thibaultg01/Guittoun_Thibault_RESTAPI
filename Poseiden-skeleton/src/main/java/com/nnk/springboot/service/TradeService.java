package com.nnk.springboot.service;

import com.nnk.springboot.model.Trade;

import java.util.List;

public interface TradeService {
    List<Trade> findAll();
    Trade getByIdOrThrow(Integer id);
    Trade save(Trade trade);
    Trade update(Integer id, Trade trade);
    void delete(Integer id);
}
