package com.nnk.springboot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nnk.springboot.Exception.ResourceNotFoundException;
import com.nnk.springboot.model.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.impl.RatingServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RatingServiceImplTest {

    private RatingRepository ratingRepository;
    private RatingServiceImpl ratingService;

    @BeforeEach
    void setUp() {
        ratingRepository = mock(RatingRepository.class);
        ratingService = new RatingServiceImpl(ratingRepository);
    }

    @Test
    void testFindAll() {
        Rating r1 = new Rating();
        r1.setId(1);
        Rating r2 = new Rating();
        r2.setId(2);

        when(ratingRepository.findAll()).thenReturn(Arrays.asList(r1, r2));

        List<Rating> ratings = ratingService.findAll();

        assertEquals(2, ratings.size());
        verify(ratingRepository, times(1)).findAll();
    }

    @Test
    void testSave() {
        Rating rating = new Rating();
        rating.setMoodysRating("Aaa");

        when(ratingRepository.save(rating)).thenReturn(rating);

        Rating saved = ratingService.save(rating);

        assertEquals("Aaa", saved.getMoodysRating());
        verify(ratingRepository, times(1)).save(rating);
    }

    @Test
    void testGetByIdOrThrowFound() {
        Rating rating = new Rating();
        rating.setId(1);

        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

        Rating result = ratingService.getByIdOrThrow(1);

        assertEquals(1, result.getId());
        verify(ratingRepository, times(1)).findById(1);
    }

    @Test
    void testGetByIdOrThrowNotFound() {
        when(ratingRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> ratingService.getByIdOrThrow(1));

        verify(ratingRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteById() {
        ratingService.deleteById(1);

        verify(ratingRepository, times(1)).deleteById(1);
    }
}