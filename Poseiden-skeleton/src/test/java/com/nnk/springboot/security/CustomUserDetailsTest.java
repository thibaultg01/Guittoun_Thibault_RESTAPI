package com.nnk.springboot.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import com.nnk.springboot.model.User;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class CustomUserDetailsTest {

    private CustomUserDetails userDetails;

    @BeforeEach
    public void setUp() {
    	User user = new User();
        user.setId(1);
        user.setUsername( "user");
        user.setPassword( "Password123!");
        user.setFullname("User Name");
        user.setRole("USER");
        userDetails = new CustomUserDetails(user);
    }

    @Test
    public void testGetUsername() {
        assertEquals("user", userDetails.getUsername());
    }

    @Test
    public void testGetPassword() {
        assertEquals("Password123!", userDetails.getPassword());
    }

    @Test
    public void testIsAccountNonExpired() {
        assertTrue(userDetails.isAccountNonExpired());
    }

    @Test
    public void testIsAccountNonLocked() {
        assertTrue(userDetails.isAccountNonLocked());
    }

    @Test
    public void testIsCredentialsNonExpired() {
        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @Test
    public void testIsEnabled() {
        assertTrue(userDetails.isEnabled());
    }
}