package com.nnk.springboot.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.model.BidList;
import com.nnk.springboot.security.CustomUserDetails;
import com.nnk.springboot.service.BidListService;

import java.sql.Timestamp;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BidListControllerTest {

    @Mock
    private BidListService bidListService;

    @Mock
    private Model model;

    @InjectMocks
    private BidListController bidListController;

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
    void listBidLists_shouldReturnListView() {
        CustomUserDetails mockUser = mock(CustomUserDetails.class);
        when(mockUser.getUsername()).thenReturn("admin");
        when(bidListService.findAll()).thenReturn(Arrays.asList(bidList));

        String view = bidListController.listBidLists(model, mockUser);

        verify(model).addAttribute("username", "admin");
        verify(model).addAttribute(eq("bidLists"), any());
        assertEquals("bidList/list", view);
    }

    @Test
    void showAddForm_shouldReturnAddView() {
        String view = bidListController.showAddForm(bidList);
        assertEquals("bidList/add", view);
    }

    @Test
    void validate_shouldRedirectToList() {
        when(bidListService.save(any(BidList.class))).thenReturn(bidList);

        String view = bidListController.validate(bidList);
        assertEquals("redirect:/bidList/list", view);
    }

    @Test
    void showUpdateForm_shouldReturnUpdateView() {
        when(bidListService.getByIdOrThrow(1)).thenReturn(bidList);

        String view = bidListController.showUpdateForm(1, model);

        verify(model, times(1)).addAttribute("bidList", bidList);
        assertEquals("bidList/update", view);
    }

    @Test
    void updateBidList_shouldRedirectToList() {
        when(bidListService.update(1, bidList)).thenReturn(bidList);

        String view = bidListController.updateBidList(1, bidList);
        assertEquals("redirect:/bidList/list", view);
    }

    @Test
    void deleteBidList_shouldRedirectToList() {
        doNothing().when(bidListService).delete(1);

        String view = bidListController.deleteBidList(1);
        assertEquals("redirect:/bidList/list", view);
    }
}