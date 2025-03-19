package com.restaurant.Restaurant.Management.Portal.service;
import com.restaurant.Restaurant.Management.Portal.model.User;
import com.restaurant.Restaurant.Management.Portal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;//handles database operations like saving and fetching

    // Injects PasswordEncoder, which encrypts passwords before storing them.
     @Autowired
    private PasswordEncoder passwordEncoder;


   //Saves a new user with an encrypted password.
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password
        return userRepository.save(user);
    }

  // Checks if a user already exists.
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
