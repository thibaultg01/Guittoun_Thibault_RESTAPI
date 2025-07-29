package com.nnk.springboot.service;

import java.util.List;

import com.nnk.springboot.model.BidList;

public interface BidListService {
    List<BidList> findAll();
    BidList getByIdOrThrow(Integer id);
    BidList save(BidList bidList);
    BidList update(Integer id, BidList bidList);
    void delete(Integer id);
}
