package com.nnk.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nnk.springboot.model.RuleName;


public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {
}
