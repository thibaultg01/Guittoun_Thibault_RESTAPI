package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.security.CustomUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/403")
    public String showErrorAccessDeniedPage(Model model,@AuthenticationPrincipal CustomUserDetails user) {
        String errorMessage= "You are not authorized for the requested data.";
        model.addAttribute("username", user.getUsername());
        model.addAttribute("errorMsg", errorMessage);
        return "403";
    }
}
