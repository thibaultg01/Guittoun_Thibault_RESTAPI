package com.nnk.springboot.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.model.Rating;
import com.nnk.springboot.service.RatingService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/rating")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    // Afficher la liste des ratings
    @GetMapping("/list")
    public String listRatings(Model model) {
        List<Rating> ratings = ratingService.findAll();
        model.addAttribute("ratings", ratings);
        return "rating/list"; // le template HTML doit exister
    }

    @GetMapping("/add")
    public String showAddForm(Rating rating) {
        return "rating/add"; // templates/rating/add.html
    }

    @PostMapping("/validate")
    public String validateRating(@ModelAttribute Rating rating, Model model) {
        ratingService.save(rating);
        model.addAttribute("ratings", ratingService.findAll());
        return "redirect:/rating/list";
    }
    

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Rating rating = ratingService.getByIdOrThrow(id);
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    @PostMapping("/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @ModelAttribute Rating rating) {
        rating.setId(id);
        ratingService.save(rating);
        return "redirect:/rating/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id) {
        ratingService.deleteById(id);
        return "redirect:/rating/list";
    }
}
