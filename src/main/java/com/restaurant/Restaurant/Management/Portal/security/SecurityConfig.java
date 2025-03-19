package com.restaurant.Restaurant.Management.Portal.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // disabled for simplicity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // Allow register & login
                        .requestMatchers("/restaurants/**").hasAuthority("OWNER") // Owners only can manage restaurants
                        .requestMatchers("/orders/**").hasAuthority("CUSTOMER") // Customers only
                        .anyRequest().authenticated()
                )
                .formLogin(withDefaults()) // Enable session-based authentication
                // .logout(logout -> logout.logoutUrl("/auth/logout").logoutSuccessUrl("/auth/login"));
                .logout(logout -> logout.logoutUrl("/auth/logout").logoutSuccessHandler((request,response,authentication)->{
                    response.setContentType("application/json");
                    response.getWriter().write("{\"message\": \"Logout successful!\"}");

                }));

        return http.build();
    }
}
