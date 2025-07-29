package com.nnk.springboot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.nnk.springboot.model.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.impl.CurvePointServiceImpl;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CurvePointServiceImplTest {

    @Mock
    private CurvePointRepository curvePointRepository;

    @InjectMocks
    private CurvePointServiceImpl curvePointService;

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
    void findAll_shouldReturnList() {
        when(curvePointRepository.findAll()).thenReturn(Arrays.asList(curvePoint));

        List<CurvePoint> result = curvePointService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(curvePointRepository, times(1)).findAll();
    }

    @Test
    void getByIdOrThrow_shouldReturnCurvePoint() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));

        CurvePoint result = curvePointService.getByIdOrThrow(1);

        assertNotNull(result);
        assertEquals(10, result.getCurveId());
        verify(curvePointRepository, times(1)).findById(1);
    }

    @Test
    void getByIdOrThrow_shouldThrowException_whenNotFound() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> curvePointService.getByIdOrThrow(1));

        assertEquals("CurvePoint not found with id: 1", exception.getMessage());
    }

    @Test
    void save_shouldReturnSavedCurvePoint() {
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

        CurvePoint saved = curvePointService.save(curvePoint);

        assertNotNull(saved);
        assertEquals(10, saved.getCurveId());
        verify(curvePointRepository, times(1)).save(curvePoint);
    }

    @Test
    void update_shouldUpdateCurvePoint() {
        when(curvePointRepository.existsById(1)).thenReturn(true);
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

        CurvePoint updated = curvePointService.update(1, curvePoint);

        assertNotNull(updated);
        verify(curvePointRepository, times(1)).save(curvePoint);
    }

    @Test
    void update_shouldThrowException_whenNotFound() {
        when(curvePointRepository.existsById(1)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> curvePointService.update(1, curvePoint));

        assertEquals("Cannot update. CurvePoint not found with id: 1", exception.getMessage());
    }

    @Test
    void delete_shouldDeleteCurvePoint() {
        when(curvePointRepository.existsById(1)).thenReturn(true);
        doNothing().when(curvePointRepository).deleteById(1);

        curvePointService.delete(1);

        verify(curvePointRepository, times(1)).deleteById(1);
    }

    @Test
    void delete_shouldThrowException_whenNotFound() {
        when(curvePointRepository.existsById(1)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> curvePointService.delete(1));

        assertEquals("Cannot delete. CurvePoint not found with id: 1", exception.getMessage());
    }
}
