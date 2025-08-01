package com.nnk.springboot.controller;

import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.model.RuleName;
import com.nnk.springboot.security.CustomUserDetails;
import com.nnk.springboot.service.RuleNameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RuleNameControllerTest {

    @Mock
    private RuleNameService ruleNameService;

    @Mock
    private Model model;

    @InjectMocks
    private RuleNameController ruleNameController;

    private RuleName ruleName;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("Test Rule");
    }

    @Test
    void listRuleNames_ShouldAddListToModel_AndReturnListView() {
        List<RuleName> ruleNames = Arrays.asList(ruleName);
        CustomUserDetails mockUser = mock(CustomUserDetails.class);
        when(mockUser.getUsername()).thenReturn("admin");

        when(ruleNameService.findAll()).thenReturn(ruleNames);

        String viewName = ruleNameController.listRuleNames(model, mockUser);

        verify(model).addAttribute("username", "admin");
        verify(model).addAttribute("ruleNames", ruleNames);
        assertEquals("ruleName/list", viewName);
    }

    @Test
    void showAddForm_ShouldReturnAddView() {
        String viewName = ruleNameController.showAddForm(new RuleName());
        assertEquals("ruleName/add", viewName);
    }

    @Test
    void validateRuleName_ShouldSaveRuleName_AndRedirect() {
        when(ruleNameService.save(any(RuleName.class))).thenReturn(ruleName);

        String viewName = ruleNameController.validateRuleName(ruleName, model);

        verify(ruleNameService, times(1)).save(ruleName);
        assertEquals("redirect:/ruleName/list", viewName);
    }

    @Test
    void showUpdateForm_ShouldRetrieveRuleName_AndReturnUpdateView() {
        when(ruleNameService.getByIdOrThrow(1)).thenReturn(ruleName);

        String viewName = ruleNameController.showUpdateForm(1, model);

        verify(model, times(1)).addAttribute("ruleName", ruleName);
        assertEquals("ruleName/update", viewName);
    }

    @Test
    void updateRuleName_ShouldSaveAndRedirect() {
        when(ruleNameService.save(any(RuleName.class))).thenReturn(ruleName);

        String viewName = ruleNameController.updateRuleName(1, ruleName);

        verify(ruleNameService, times(1)).save(ruleName);
        assertEquals("redirect:/ruleName/list", viewName);
    }

    @Test
    void deleteRuleName_ShouldDeleteAndRedirect() {
        doNothing().when(ruleNameService).deleteById(1);

        String viewName = ruleNameController.deleteRuleName(1);

        verify(ruleNameService, times(1)).deleteById(1);
        assertEquals("redirect:/ruleName/list", viewName);
    }
}
