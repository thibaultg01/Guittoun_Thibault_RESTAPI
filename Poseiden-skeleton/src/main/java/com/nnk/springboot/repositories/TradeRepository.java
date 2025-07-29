package com.nnk.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nnk.springboot.model.Trade;


public interface TradeRepository extends JpaRepository<Trade, Integer> {
}
