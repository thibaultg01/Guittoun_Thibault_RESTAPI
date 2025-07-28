package com.nnk.springboot.service;

import com.nnk.springboot.model.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.RuleNameService;
import com.nnk.springboot.service.impl.RuleNameServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RuleNameServiceImplTest {

    @Mock
    private RuleNameRepository ruleNameRepository;

    @InjectMocks
    private RuleNameServiceImpl ruleNameService;

    private RuleName ruleName;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("Test Rule");
    }

    @Test
    void findAll_ShouldReturnAllRuleNames() {
        when(ruleNameRepository.findAll()).thenReturn(Arrays.asList(ruleName));

        List<RuleName> result = ruleNameService.findAll();

        assertEquals(1, result.size());
        verify(ruleNameRepository, times(1)).findAll();
    }

    @Test
    void getByIdOrThrow_ShouldReturnRuleName_WhenExists() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));

        RuleName result = ruleNameService.getByIdOrThrow(1);

        assertNotNull(result);
        assertEquals("Test Rule", result.getName());
    }

    @Test
    void getByIdOrThrow_ShouldThrowException_WhenNotFound() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> ruleNameService.getByIdOrThrow(1));
    }

    @Test
    void save_ShouldPersistRuleName() {
        when(ruleNameRepository.save(ruleName)).thenReturn(ruleName);

        RuleName saved = ruleNameService.save(ruleName);

        assertEquals("Test Rule", saved.getName());
        verify(ruleNameRepository, times(1)).save(ruleName);
    }

    @Test
    void update_ShouldUpdateRuleName() {
        ruleName.setDescription("Updated");
        when(ruleNameRepository.save(ruleName)).thenReturn(ruleName);

        RuleName updated = ruleNameService.update(1, ruleName);

        assertEquals("Updated", updated.getDescription());
        assertEquals(1, updated.getId());
    }

    @Test
    void deleteById_ShouldInvokeRepository() {
        doNothing().when(ruleNameRepository).deleteById(1);

        ruleNameService.deleteById(1);

        verify(ruleNameRepository, times(1)).deleteById(1);
    }
}
