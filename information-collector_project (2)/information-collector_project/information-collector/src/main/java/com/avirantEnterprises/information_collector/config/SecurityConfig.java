package com.avirantEnterprises.information_collector.config;

import com.avirantEnterprises.information_collector.repository.ProfileRepository;
import com.avirantEnterprises.information_collector.service.LoginAttemptService;
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
    private final LoginAttemptService loginAttemptService;

    public SecurityConfig(ProfileRepository profileRepository, LoginAttemptService loginAttemptService) {
        this.profileRepository = profileRepository;
        this.loginAttemptService = loginAttemptService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/signup", "/login", "/Images/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .successHandler(customAuthenticationSuccessHandler())
                        .failureHandler((request, response, exception) -> {
                            String username = request.getParameter("username");
                            if (username != null && loginAttemptService.isBlocked(username)) {
                                // Redirect with 'blocked=true' if the user is blocked
                                response.sendRedirect("/login?error=true&blocked=true");
                            } else {
                                loginAttemptService.loginFailed(username);
                                response.sendRedirect("/login?error=true");
                            }
                        })
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/error/unauthorized")
                )
                .authenticationProvider(authenticationProvider());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            // Check if the user is blocked
            if (loginAttemptService.isBlocked(username)) {
                throw new UsernameNotFoundException("User is blocked due to multiple failed login attempts: " + username);
            }

            if ("admin".equals(username)) {
                return User.withUsername("admin")
                        .password(passwordEncoder().encode("admin123"))
                        .roles("ADMIN")
                        .build();
            }

            // Look up the user in the database
            return profileRepository.findByUsername(username)
                    .map(user -> User.withUsername(user.getUsername())
                            .password(user.getPassword())
                            .roles(user.getRole())
                            .build())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            String username = authentication.getName();
            // Reset login attempts on successful login
            loginAttemptService.loginSucceeded(username);

            var roles = authentication.getAuthorities();
            if (roles.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
                response.sendRedirect("/admin/dashboard");
            } else if (roles.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"))) {
                response.sendRedirect("/user/dashboard");
            } else {
                response.sendRedirect("/error/unauthorized");
            }
        };
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
