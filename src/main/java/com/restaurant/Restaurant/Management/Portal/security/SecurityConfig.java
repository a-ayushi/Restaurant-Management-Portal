/// Updated SecurityConfig.java

package com.restaurant.Restaurant.Management.Portal.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // Allow authentication endpoints
                        .requestMatchers("/restaurants/**").permitAll() // Public access for viewing restaurants
                        .anyRequest().permitAll() // Allow all other requests
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // Ensure session is created
                );

        return http.build();
    }
}












//package com.restaurant.Restaurant.Management.Portal.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())  // Disable CSRF for testing
//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest().permitAll()  // Allow all requests without authentication
//                );
//
//        return http.build();
//    }
//}
