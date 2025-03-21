package com.restaurant.Restaurant.Management.Portal.controller;

import com.restaurant.Restaurant.Management.Portal.model.User;
import com.restaurant.Restaurant.Management.Portal.service.UserService;
import com.restaurant.Restaurant.Management.Portal.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody User user) {
        if (user.getRole() == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Role must be CUSTOMER or OWNER"));
        }
        userService.registerUser(user);
        return ResponseEntity.ok(Collections.singletonMap("message", "User registered successfully!"));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user) {
        Optional<User> existingUser = userService.findByEmail(user.getEmail());
    
        if (existingUser.isPresent() &&
                passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword())) {
    
            // Generate JWT token using the user's email (or unique identifier)
            String token = jwtUtil.generateToken(existingUser.get().getEmail());
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Invalid credentials"));
        }
    }
    

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout() {
        // With JWT, logout is typically handled client side by deleting the token.
        return ResponseEntity.ok(Collections.singletonMap("message", "Logout successful!"));
    }
}
