package com.avirantEnterprises.information_collector.config;

import com.avirantEnterprises.information_collector.repository.ProfileRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    private final ProfileRepository profileRepository;

    // Constructor-based injection of ProfileRepository
    public SecurityConfig(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/signup", "/login", "/Images/**").permitAll() // Allow unauthenticated access
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Restrict admin endpoints to ROLE_ADMIN
                        .requestMatchers("/user/**").hasRole("USER") // Restrict user endpoints to ROLE_USER
                        .anyRequest().authenticated() // Require authentication for all other endpoints
                )
                .formLogin(login -> login
                        .loginPage("/login") // Custom login page
                        .successHandler(customAuthenticationSuccessHandler()) // Custom success handler for role-based redirects
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout") // Redirect to login page after logout
                        .permitAll()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/error/unauthorized") // Redirect to an unauthorized page for access denied
                )
                .authenticationProvider(authenticationProvider()); // Use custom authentication provider
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            // Hardcoded admin credentials
            if ("admin".equals(username)) {
                return User.withUsername("admin")
                        .password(passwordEncoder().encode("admin123")) // Replace with your desired admin password
                        .roles("ADMIN") // Hardcoded role
                        .build();
            }

            // Check database for other users
            return profileRepository.findByUsername(username)
                    .map(user -> User.withUsername(user.getUsername())
                            .password(user.getPassword()) // Already encoded in the database
                            .roles(user.getRole()) // Use the role from the database
                            .build())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        };
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            var roles = authentication.getAuthorities();
            if (roles.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
                response.sendRedirect("/admin/dashboard"); // Redirect admins to their dashboard
            } else if (roles.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"))) {
                response.sendRedirect("/user/dashboard"); // Redirect users to their dashboard
            } else {
                response.sendRedirect("/error/unauthorized"); // Redirect to unauthorized page if no valid role
            }
        };
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService()); // Use the combined UserDetailsService
        provider.setPasswordEncoder(passwordEncoder()); // Use the password encoder
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
