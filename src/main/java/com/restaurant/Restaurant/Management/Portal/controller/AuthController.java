package com.restaurant.Restaurant.Management.Portal.controller;

import com.restaurant.Restaurant.Management.Portal.model.User;
import com.restaurant.Restaurant.Management.Portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:63342")
public class AuthController {
    @Autowired // provides the instance of userService without manually creating it using new
    private UserService userService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        if (user.getRole() == null) {
            return "Error: Role must be CUSTOMER or OWNER";
        }
        userService.registerUser(user);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        Optional<User> existingUser = userService.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {

            response.put("message", "Login successful!");
            response.put("userId", existingUser.get().getId());
            response.put("role", existingUser.get().getRole().toString());
            return response;
        } else {
            response.put("message", "Invalid credentials");
            return response;
        }
    }
    @PostMapping("/logout")
    public String logout() {
        return "Logout successful!";
    }
}
