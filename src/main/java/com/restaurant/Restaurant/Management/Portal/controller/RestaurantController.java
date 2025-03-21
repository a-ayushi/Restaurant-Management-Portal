package com.restaurant.Restaurant.Management.Portal.controller;

import com.restaurant.Restaurant.Management.Portal.model.Restaurant;
import com.restaurant.Restaurant.Management.Portal.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    // 1. POST /restaurants -> Only logged-in owners can create a restaurant.
    @PostMapping
    public Restaurant createRestaurant(@RequestBody Restaurant restaurant) {
        // Get the currently authenticated user's details
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            throw new RuntimeException("User not logged in.");
        }

        // Here, the username (in our case, the email) is retrieved from the Authentication object.
        String ownerEmail = auth.getName();
        // You might want to load the owner's ID from your database based on the email
        // For example:
        // Long ownerId = userService.findByEmail(ownerEmail).getId();
        // For now, we'll assume your restaurantService.createRestaurant method can work with the email:
        return restaurantService.createRestaurant(restaurant, ownerEmail);
    }

    // 2. GET /restaurants -> Anyone can view all restaurants.
    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    // 3. GET /restaurants/{id} -> Fetch a specific restaurant by ID.
    @GetMapping("/{id}")
    public Optional<Restaurant> getRestaurantById(@PathVariable Long id) {
        return restaurantService.getRestaurantById(id);
    }

    // 4. PUT /restaurants/{id} -> Only the owner of that restaurant can update it.
    @PutMapping("/{id}")
    public Restaurant updateRestaurant(@PathVariable Long id, @RequestBody Restaurant restaurant) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            throw new RuntimeException("User not logged in.");
        }
        String ownerEmail = auth.getName();
        return restaurantService.updateRestaurant(id, restaurant, ownerEmail);
    }

    // 5. DELETE /restaurants/{id} -> Only the owner of that restaurant can delete it.
    @DeleteMapping("/{id}")
    public String deleteRestaurant(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            throw new RuntimeException("User not logged in.");
        }
        String ownerEmail = auth.getName();
        restaurantService.deleteRestaurant(id, ownerEmail);
        return "Restaurant deleted successfully!";
    }
}
