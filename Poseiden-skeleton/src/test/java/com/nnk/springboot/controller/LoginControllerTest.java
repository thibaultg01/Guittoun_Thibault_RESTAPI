package com.nnk.springboot.controller;

import com.nnk.springboot.controllers.LoginController;
import com.nnk.springboot.model.User;
import com.nnk.springboot.security.CustomUserDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.ui.Model;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Please sign in")));
    }

    @Test
    void testAccessDeniedPageWithUser() throws Exception {
    	User user = new User();
        user.setId(1);
        user.setUsername( "mockuser");
        user.setPassword( "Password123!");
        user.setFullname("Mock User");
        user.setRole("USER");
        CustomUserDetails mockUser = new CustomUserDetails(user);

        mockMvc.perform(get("/403").with(user(mockUser)))
                .andExpect(status().isOk())
                .andExpect(view().name("403"))
                .andExpect(model().attribute("username", "mockuser"))
                .andExpect(model().attribute("errorMsg", "You are not authorized for the requested data."));
    }
}
