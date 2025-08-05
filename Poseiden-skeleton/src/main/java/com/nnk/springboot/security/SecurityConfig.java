package com.nnk.springboot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig configures Spring Security for the application.
 *
 * It defines the access rules for HTTP endpoints, handles authentication and logout behavior,
 * and provides the password encoding mechanism.
 *
 * This configuration uses session-based authentication and a custom login form.
 * It ensures that only administrators can access sensitive endpoints like /user/*,
 * while authenticated users with roles USER or ADMIN can access other parts of the application.
 */
@Configuration
public class SecurityConfig {

	/**
     * Configures the HTTP security settings for the application.
     *
     * It defines:
     * - Public access to the login page and static resources
     * - Restricted access to /user/** for ADMIN users only
     * - Authenticated access to all other endpoints for USER and ADMIN roles
     * - A custom login page and default success URL
     * - A logout URL and redirect upon successful logout
     * - A custom access denied page for unauthorized requests
     *
     * @param http the HttpSecurity object used to configure web security
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs while building the security configuration
     */
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/css/**").permitAll()
                .requestMatchers("/user/**").hasRole("ADMIN") // seul l'admin peut accéder à /user/
                .anyRequest().hasAnyRole("USER", "ADMIN")     // tout le reste accessible aux 2 roles
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login") 
                .defaultSuccessUrl("/bidList/list", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout") 
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            )
            .exceptionHandling(exception -> exception
                    .accessDeniedPage("/403")
                );

        return http.build();
    }

	/**
     * Defines the password encoder used to hash user passwords.
     *
     * This encoder uses the BCrypt algorithm, which is recommended for password security.
     *
     * @return a BCryptPasswordEncoder instance
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}