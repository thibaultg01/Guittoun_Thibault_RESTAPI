package com.nnk.springboot.service.impl;

import com.nnk.springboot.Exception.ResourceNotFoundException;
import com.nnk.springboot.model.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.RatingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    @Override
    public Rating save(Rating rating) {
        return ratingRepository.save(rating);
    }
   
    
    @Override
    public Rating getByIdOrThrow(Integer id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rating with id " + id + " not found"));
    }


    @Override
    public void deleteById(Integer id) {
        ratingRepository.deleteById(id);
    }
}