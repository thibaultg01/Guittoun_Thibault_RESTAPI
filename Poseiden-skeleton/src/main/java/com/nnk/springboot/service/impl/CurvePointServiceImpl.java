package com.nnk.springboot.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nnk.springboot.model.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.CurvePointService;

@Service
public class CurvePointServiceImpl implements CurvePointService {

	private final CurvePointRepository curvePointRepository;

	public CurvePointServiceImpl(CurvePointRepository curvePointRepository) {
		this.curvePointRepository = curvePointRepository;
	}

	@Override
	public List<CurvePoint> findAll() {
		return curvePointRepository.findAll();
	}

	@Override
	public CurvePoint getByIdOrThrow(Integer id) {
		return curvePointRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("CurvePoint not found with id: " + id));
	}

	@Override
	public CurvePoint save(CurvePoint curvePoint) {
		return curvePointRepository.save(curvePoint);
	}

	@Override
	public CurvePoint update(Integer id, CurvePoint curvePoint) {
		if (!curvePointRepository.existsById(id)) {
			throw new RuntimeException("Cannot update. CurvePoint not found with id: " + id);
		}
		curvePoint.setId(id);
		return curvePointRepository.save(curvePoint);
	}

	@Override
	public void delete(Integer id) {
		if (!curvePointRepository.existsById(id)) {
			throw new RuntimeException("Cannot delete. CurvePoint not found with id: " + id);
		}
		curvePointRepository.deleteById(id);
	}
}