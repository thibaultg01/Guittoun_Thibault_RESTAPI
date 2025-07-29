package com.nnk.springboot.service;

import java.util.List;

import com.nnk.springboot.model.CurvePoint;

public interface CurvePointService {
    List<CurvePoint> findAll();
    CurvePoint getByIdOrThrow(Integer id);
    CurvePoint save(CurvePoint curvePoint);
    CurvePoint update(Integer id, CurvePoint curvePoint);
    void delete(Integer id);
}