package com.nnk.springboot.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.nnk.springboot.Exception.InvalidValidatePasswordException;
import com.nnk.springboot.model.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.impl.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;



class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1);
        user.setUsername("testUser");
        user.setFullname("Test User");
        user.setRole("USER");
        user.setPassword("Valid123!"); // mot de passe valide
    }

    @Test
    void findAll_shouldReturnList() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        List<User> result = userService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getByIdOrThrow_shouldReturnUser() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User result = userService.getByIdOrThrow(1);

        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void getByIdOrThrow_shouldThrowException_whenNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.getByIdOrThrow(1));
        assertEquals("User not found with id: 1", exception.getMessage());
    }

    @Test
    void save_shouldEncodePasswordAndSaveUser() {
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        User saved = userService.save(user);

        assertNotNull(saved);
        assertTrue(new BCryptPasswordEncoder().matches("Valid123!", saved.getPassword()));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void save_shouldThrowException_whenPasswordInvalid() {
        user.setPassword("weak"); // mot de passe invalide (pas assez complexe)

        InvalidValidatePasswordException exception = assertThrows(InvalidValidatePasswordException.class, () -> userService.save(user));

        assertEquals("Password must be at least 8 characters long, include a capital letter, a number, and a special character.", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void update_shouldRehashPasswordAndSaveUser() {
        when(userRepository.existsById(1)).thenReturn(true);
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        User updated = userService.update(1, user);

        assertNotNull(updated);
        assertTrue(new BCryptPasswordEncoder().matches("Valid123!", updated.getPassword()));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void delete_shouldDeleteUser() {
        when(userRepository.existsById(1)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1);

        userService.delete(1);

        verify(userRepository, times(1)).deleteById(1);
    }

    @Test
    void delete_shouldThrowException_whenNotFound() {
        when(userRepository.existsById(1)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.delete(1));
        assertEquals("Cannot delete. User not found with id: 1", exception.getMessage());
    }
}
