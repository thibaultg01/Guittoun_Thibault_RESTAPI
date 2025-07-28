package com.nnk.springboot.service;

import com.nnk.springboot.model.RuleName;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for RuleName.
 * Provides an abstraction for CRUD operations.
 */
public interface RuleNameService {

    List<RuleName> findAll();

    RuleName save(RuleName ruleName);

    RuleName update(Integer id, RuleName ruleName);

    void deleteById(Integer id);
    
    RuleName getByIdOrThrow(Integer id);
}