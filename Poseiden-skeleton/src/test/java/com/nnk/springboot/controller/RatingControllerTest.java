package com.nnk.springboot.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.model.Rating;
import com.nnk.springboot.service.RatingService;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RatingControllerTest {

    private RatingService ratingService;
    private RatingController ratingController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        ratingService = mock(RatingService.class);
        ratingController = new RatingController(ratingService);
        mockMvc = MockMvcBuilders.standaloneSetup(ratingController).build();
    }

    @Test
    void testListRatings() throws Exception {
        when(ratingService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"));

        verify(ratingService, times(1)).findAll();
    }

    @Test
    void testShowAddForm() throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    void testValidateRating() throws Exception {
        Rating rating = new Rating();
        when(ratingService.save(Mockito.any(Rating.class))).thenReturn(rating);

        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating", "Aaa")
                        .param("sandPRating", "AA")
                        .param("fitchRating", "F1")
                        .param("orderNumber", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService, times(1)).save(Mockito.any(Rating.class));
    }

    @Test
    void testShowUpdateForm() throws Exception {
        Rating rating = new Rating();
        rating.setId(1);
        when(ratingService.getByIdOrThrow(1)).thenReturn(rating);

        mockMvc.perform(get("/rating/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attributeExists("rating"));

        verify(ratingService, times(1)).getByIdOrThrow(1);
    }

    @Test
    void testUpdateRating() throws Exception {
        Rating rating = new Rating();
        rating.setId(1);
        when(ratingService.save(Mockito.any(Rating.class))).thenReturn(rating);

        mockMvc.perform(post("/rating/update/1")
                        .param("moodysRating", "Bbb")
                        .param("sandPRating", "BB")
                        .param("fitchRating", "F2")
                        .param("orderNumber", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService, times(1)).save(Mockito.any(Rating.class));
    }

    @Test
    void testDeleteRating() throws Exception {
        doNothing().when(ratingService).deleteById(1);

        mockMvc.perform(get("/rating/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService, times(1)).deleteById(1);
    }
}
