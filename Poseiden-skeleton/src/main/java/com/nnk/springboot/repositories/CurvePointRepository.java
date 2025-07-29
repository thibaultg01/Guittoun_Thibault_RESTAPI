package com.nnk.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nnk.springboot.model.CurvePoint;


public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {

}
