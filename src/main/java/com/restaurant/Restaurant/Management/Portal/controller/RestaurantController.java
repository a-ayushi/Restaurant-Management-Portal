package com.restaurant.Restaurant.Management.Portal.controller;

import com.restaurant.Restaurant.Management.Portal.model.Restaurant;
import com.restaurant.Restaurant.Management.Portal.service.RestaurantService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:63342", allowCredentials = "true")
@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    // ✅ 1. CREATE a restaurant (Only OWNER can create)
    @PostMapping
    public ResponseEntity<?> createRestaurant(@RequestBody Restaurant restaurant, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        String role = (String) session.getAttribute("role");

        if (userId == null || role == null) {
            return ResponseEntity.status(401).body("Unauthorized: Please log in.");
        }

        if (!"OWNER".equals(role)) {
            return ResponseEntity.status(403).body("Forbidden: Only OWNERS can create restaurants.");
        }
        Restaurant createdRestaurant = restaurantService.createRestaurant(restaurant, userId);

            return ResponseEntity.ok(createdRestaurant);

    }

    // ✅ 2. GET all restaurants (Anyone can access)
    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    // ✅ 3. GET a restaurant by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getRestaurantById(@PathVariable Long id) {
        Optional<Restaurant> restaurant = restaurantService.getRestaurantById(id);

        if (restaurant.isPresent()) {
            return ResponseEntity.ok(restaurant.get());  // ✅ Returns Restaurant when found
        } else {
            return ResponseEntity.status(404).body("Restaurant not found.");  // ✅ Returns String when not found
        }
    }



    // ✅ 4. UPDATE restaurant (Only OWNER of that restaurant can update)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRestaurant(@PathVariable Long id, @RequestBody Restaurant restaurant, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(401).body("Unauthorized: Please log in.");
        }

        try {
            Restaurant updated = restaurantService.updateRestaurant(id, restaurant, userId);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    // ✅ 5. DELETE restaurant (Only OWNER of that restaurant can delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRestaurant(@PathVariable Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(401).body("Unauthorized: Please log in.");
        }

        try {
            restaurantService.deleteRestaurant(id, userId);
            return ResponseEntity.ok("Restaurant deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
}