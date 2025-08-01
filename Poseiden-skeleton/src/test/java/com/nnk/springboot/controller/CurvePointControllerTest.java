package com.nnk.springboot.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.nnk.springboot.controllers.CurvePointController;
import com.nnk.springboot.model.CurvePoint;
import com.nnk.springboot.security.CustomUserDetails;
import com.nnk.springboot.service.CurvePointService;

import java.sql.Timestamp;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CurvePointControllerTest {

    @Mock
    private CurvePointService curvePointService;

    @Mock
    private Model model;

    @InjectMocks
    private CurvePointController curvePointController;

    private CurvePoint curvePoint;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(10);
        curvePoint.setTerm(5.0);
        curvePoint.setValue(10.0);
        curvePoint.setAsOfDate(new Timestamp(System.currentTimeMillis()));
        curvePoint.setCreationDate(new Timestamp(System.currentTimeMillis()));
    }

    @Test
    void listCurvePoints_shouldReturnListView() {
        CustomUserDetails mockUser = mock(CustomUserDetails.class);
        when(mockUser.getUsername()).thenReturn("admin");

        when(curvePointService.findAll()).thenReturn(Arrays.asList(curvePoint));

        String view = curvePointController.listCurvePoints(model, mockUser);

        verify(model).addAttribute("username", "admin");
        verify(model).addAttribute(eq("curvePoints"), any());
        assertEquals("curvePoint/list", view);
    }

    @Test
    void showAddForm_shouldReturnAddView() {
        String view = curvePointController.showAddForm(curvePoint);
        assertEquals("curvePoint/add", view);
    }

    @Test
    void validate_shouldRedirectToList() {
        when(curvePointService.save(any(CurvePoint.class))).thenReturn(curvePoint);

        String view = curvePointController.validate(curvePoint);
        assertEquals("redirect:/curvePoint/list", view);
    }

    @Test
    void showUpdateForm_shouldReturnUpdateView() {
        when(curvePointService.getByIdOrThrow(1)).thenReturn(curvePoint);

        String view = curvePointController.showUpdateForm(1, model);

        verify(model, times(1)).addAttribute("curvePoint", curvePoint);
        assertEquals("curvePoint/update", view);
    }

    @Test
    void updateCurvePoint_shouldRedirectToList() {
        when(curvePointService.update(1, curvePoint)).thenReturn(curvePoint);

        String view = curvePointController.updateCurvePoint(1, curvePoint);
        assertEquals("redirect:/curvePoint/list", view);
    }

    @Test
    void deleteCurvePoint_shouldRedirectToList() {
        doNothing().when(curvePointService).delete(1);

        String view = curvePointController.deleteCurvePoint(1);
        assertEquals("redirect:/curvePoint/list", view);
    }
}