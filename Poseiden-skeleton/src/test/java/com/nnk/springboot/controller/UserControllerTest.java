package com.nnk.springboot.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.model.User;
import com.nnk.springboot.security.CustomUserDetails;
import com.nnk.springboot.service.UserService;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @InjectMocks
    private UserController userController;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1);
        user.setUsername("testUser");
        user.setFullname("Test User");
        user.setRole("USER");
        user.setPassword("Valid123!");
    }

    @Test
    void listUsers_shouldReturnListView() {
    	CustomUserDetails mockUser = mock(CustomUserDetails.class);
	    when(mockUser.getUsername()).thenReturn("admin");
        when(userService.findAll()).thenReturn(Arrays.asList(user));

        String view = userController.listUsers(model, mockUser);

        verify(model).addAttribute("username", "admin");
        verify(model, times(1)).addAttribute(eq("users"), any());
        assertEquals("user/list", view);
    }

    @Test
    void showAddForm_shouldReturnAddView() {
        String view = userController.showAddForm(user);
        assertEquals("user/add", view);
    }

    @Test
    void validate_shouldRedirectToList() {
        when(userService.save(any(User.class))).thenReturn(user);

        String view = userController.validate(user);
        assertEquals("redirect:/user/list", view);
    }

    @Test
    void showUpdateForm_shouldReturnUpdateView() {
        when(userService.getByIdOrThrow(1)).thenReturn(user);

        String view = userController.showUpdateForm(1, model);

        verify(model, times(1)).addAttribute("user", user);
        assertEquals("user/update", view);
    }

    @Test
    void updateUser_shouldRedirectToList() {
        when(userService.update(1, user)).thenReturn(user);

        String view = userController.updateUser(1, user);
        assertEquals("redirect:/user/list", view);
    }

    @Test
    void deleteUser_shouldRedirectToList() {
        doNothing().when(userService).delete(1);

        String view = userController.deleteUser(1);
        assertEquals("redirect:/user/list", view);
    }
}
