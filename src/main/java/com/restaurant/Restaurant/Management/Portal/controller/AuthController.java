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

import com.restaurant.Restaurant.Management.Portal.model.Role;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:63342")
public class AuthController {
    @Autowired // provides the instance of userService without manually creating it, using new
    private UserService userService;

//1. API for user Register
@PostMapping("/register")
public ResponseEntity<String> registerUser(@RequestBody User user) {

    userService.registerUser(user);

    if (user.getRole().equals(Role.CUSTOMER)) {
        return ResponseEntity.ok("Registered as CUSTOMER. Redirecting to login...");
    } else {
        return ResponseEntity.ok("Registered as OWNER.");
    }
}


    //2. API for user login
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        Optional<User> existingUser = userService.findByEmail(user.getEmail());
    if (existingUser.isPresent()) {
    User userFromDb = existingUser.get();
    if (userFromDb.getPassword().equals(user.getPassword())) {
            response.put("message", "Login successful!");
            response.put("userId", existingUser.get().getId());
            response.put("role", existingUser.get().getRole().toString());
            return response;
        } else {
            response.put("message", "Invalid credentials");
            return response;
        }
    }
    return response;
}


    //3. Api for logout
    @PostMapping("/logout")
    public String logout() {
        return "Logout successful!";
    }
}
