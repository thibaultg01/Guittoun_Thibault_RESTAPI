package com.nnk.springboot.service.impl;

import com.nnk.springboot.model.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.RuleNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleNameServiceImpl implements RuleNameService {

	@Autowired
	private RuleNameRepository ruleNameRepository;

	@Override
	public List<RuleName> findAll() {
		return ruleNameRepository.findAll();
	}

	@Override
	public RuleName save(RuleName ruleName) {
		return ruleNameRepository.save(ruleName);
	}

	@Override
	public RuleName update(Integer id, RuleName ruleName) {
		ruleName.setId(id);
		return ruleNameRepository.save(ruleName);
	}

	@Override
	public void deleteById(Integer id) {
		ruleNameRepository.deleteById(id);
	}
	
	@Override
    public RuleName getByIdOrThrow(Integer id) {
        return ruleNameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RuleName not found with id: " + id));
    }
}
