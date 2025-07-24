package com.nnk.springboot.service;

import com.nnk.springboot.model.Rating;
import java.util.List;
import java.util.Optional;

public interface RatingService {
    List<Rating> findAll();
    Rating save(Rating rating);
    void deleteById(Integer id);
    Rating getByIdOrThrow(Integer id);
}
