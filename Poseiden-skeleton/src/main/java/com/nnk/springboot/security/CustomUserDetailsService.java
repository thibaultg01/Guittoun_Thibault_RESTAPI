package com.nnk.springboot.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nnk.springboot.model.User;
import com.nnk.springboot.repositories.UserRepository;

import java.util.Collections;

/**
 * CustomUserDetailsService is a custom implementation of Spring Security's UserDetailsService.
 *
 * It is used by the authentication process to retrieve user information from the database.
 * This service loads a user by their username and returns a UserDetails object
 * required by Spring Security for authentication and authorization.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads the user details for a given username.
     *
     * This method is called automatically by Spring Security during authentication.
     * It looks up the user by username and wraps the User entity into a CustomUserDetails object.
     *
     * @param username the username of the user to retrieve
     * @return a UserDetails object containing the user's credentials and roles
     * @throws UsernameNotFoundException if the user is not found in the database
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + username));
        return new CustomUserDetails(user);
    }
}