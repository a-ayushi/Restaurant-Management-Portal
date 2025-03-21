package com.restaurant.Restaurant.Management.Portal.service;

import com.restaurant.Restaurant.Management.Portal.model.User;
import com.restaurant.Restaurant.Management.Portal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Here, we treat the email as the username
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        
        // Convert the user's role to a GrantedAuthority.
        // Assuming user.getRole() returns a role like "OWNER" or "CUSTOMER"
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword()) // The password should be already encoded
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(user.getRole().toString())))
                .build();
    }
}
