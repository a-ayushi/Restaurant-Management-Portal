package com.restaurant.Restaurant.Management.Portal.controller;

import com.restaurant.Restaurant.Management.Portal.model.Restaurant;
import com.restaurant.Restaurant.Management.Portal.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    // ✅ 1. CREATE a restaurant (No authentication required)
    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant createdRestaurant = restaurantService.createRestaurant(restaurant);
        return ResponseEntity.ok(createdRestaurant);
    }

    // ✅ 2. GET all restaurants
    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    // ✅ 3. GET a restaurant by ID
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getRestaurantById(@PathVariable Long id) {
//        Optional<Restaurant> restaurant = restaurantService.getRestaurantById(id);
//
//        return restaurant.map(ResponseEntity::ok)  // ✅ Return the restaurant if found
//                .orElseGet(() -> ResponseEntity.status(404).body("Restaurant not found"));  // ✅ Return String when not found
//    }
}
