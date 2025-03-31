package com.restaurant.Restaurant.Management.Portal.service;
import com.restaurant.Restaurant.Management.Portal.model.User;
import com.restaurant.Restaurant.Management.Portal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

   // used when a new user registers
    //used in user registration APIs
    public User registerUser(User user) {
        // Check if user already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Error: Email is already in use!");
        }
        return userRepository.save(user);  // saves the new user in DB
    }


    //used during the login to check if email belong to registered user
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}

