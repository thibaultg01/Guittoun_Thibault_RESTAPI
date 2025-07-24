package com.nnk.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nnk.springboot.model.Rating;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

}
