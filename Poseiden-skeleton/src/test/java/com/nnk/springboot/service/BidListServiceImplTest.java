package com.nnk.springboot.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.nnk.springboot.model.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.impl.BidListServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BidListServiceImplTest {

    @Mock
    private BidListRepository bidListRepository;

    @InjectMocks
    private BidListServiceImpl bidListService;

    private BidList bidList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bidList = new BidList();
        bidList.setBidListId(1);
        bidList.setAccount("TestAccount");
        bidList.setType("TestType");
        bidList.setBidQuantity(10.0);
    }

    @Test
    void findAll_shouldReturnList() {
        when(bidListRepository.findAll()).thenReturn(Arrays.asList(bidList));

        List<BidList> result = bidListService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(bidListRepository, times(1)).findAll();
    }

    @Test
    void getByIdOrThrow_shouldReturnBidList() {
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));

        BidList result = bidListService.getByIdOrThrow(1);

        assertNotNull(result);
        assertEquals("TestAccount", result.getAccount());
        verify(bidListRepository, times(1)).findById(1);
    }

    @Test
    void getByIdOrThrow_shouldThrowException_whenNotFound() {
        when(bidListRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> bidListService.getByIdOrThrow(1));

        assertEquals("BidList not found with id: 1", exception.getMessage());
    }

    @Test
    void save_shouldReturnSavedBidList() {
        when(bidListRepository.save(any(BidList.class))).thenReturn(bidList);

        BidList saved = bidListService.save(bidList);

        assertNotNull(saved);
        assertEquals("TestAccount", saved.getAccount());
        verify(bidListRepository, times(1)).save(bidList);
    }

    @Test
    void update_shouldUpdateBidList() {
        when(bidListRepository.existsById(1)).thenReturn(true);
        when(bidListRepository.save(any(BidList.class))).thenReturn(bidList);

        BidList updated = bidListService.update(1, bidList);

        assertNotNull(updated);
        verify(bidListRepository, times(1)).save(bidList);
    }

    @Test
    void update_shouldThrowException_whenNotFound() {
        when(bidListRepository.existsById(1)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> bidListService.update(1, bidList));

        assertEquals("Cannot update. BidList not found with id: 1", exception.getMessage());
    }

    @Test
    void delete_shouldDeleteBidList() {
        when(bidListRepository.existsById(1)).thenReturn(true);
        doNothing().when(bidListRepository).deleteById(1);

        bidListService.delete(1);

        verify(bidListRepository, times(1)).deleteById(1);
    }

    @Test
    void delete_shouldThrowException_whenNotFound() {
        when(bidListRepository.existsById(1)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> bidListService.delete(1));

        assertEquals("Cannot delete. BidList not found with id: 1", exception.getMessage());
    }
}