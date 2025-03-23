package com.restaurant.Restaurant.Management.Portal.controller;

import com.restaurant.Restaurant.Management.Portal.model.User;
import com.restaurant.Restaurant.Management.Portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:63342")
public class AuthController {
    @Autowired // provides the instance of userService without manually creating it using new
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        if (user.getRole() == null) {
            return "Error: Role must be CUSTOMER or OWNER";
        }
        userService.registerUser(user);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user, HttpSession session) {
//        Optional<User> existingUser = userService.findByEmail(user.getEmail());
//
//        if (existingUser.isPresent() &&
//                passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword())) {
//
//            session.setAttribute("userId", existingUser.get().getId()); // Store user in session
//            return "Login successful!";
//        } else {
//            return "Invalid credentials";
//        }
        session.setAttribute("userId", user);
        return "Logged in successfully!";
    }
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Destroy session
        return "Logout successful!";
    }
}
