package com.nnk.springboot.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nnk.springboot.model.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.BidListService;

@Service
public class BidListServiceImpl implements BidListService {

    private final BidListRepository bidListRepository;

    public BidListServiceImpl(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    @Override
    public List<BidList> findAll() {
        return bidListRepository.findAll();
    }

    @Override
    public BidList getByIdOrThrow(Integer id) {
        return bidListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BidList not found with id: " + id));
    }

    @Override
    public BidList save(BidList bidList) {
        return bidListRepository.save(bidList);
    }

    @Override
    public BidList update(Integer id, BidList bidList) {
        if (!bidListRepository.existsById(id)) {
            throw new RuntimeException("Cannot update. BidList not found with id: " + id);
        }
        bidList.setBidListId(id);
        return bidListRepository.save(bidList);
    }

    @Override
    public void delete(Integer id) {
        if (!bidListRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete. BidList not found with id: " + id);
        }
        bidListRepository.deleteById(id);
    }
}