
package com.restaurant.Restaurant.Management.Portal.service;

import com.restaurant.Restaurant.Management.Portal.model.Restaurant;
import com.restaurant.Restaurant.Management.Portal.model.User;
import com.restaurant.Restaurant.Management.Portal.repository.RestaurantRepository;
import com.restaurant.Restaurant.Management.Portal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    // OPERATION 1: Only an OWNER can create a restaurant.
    public Restaurant createRestaurant(Restaurant restaurant, Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("User with ID " + ownerId + " not found."));

        if (!"OWNER".equals(owner.getRole())) {
            throw new RuntimeException("Access denied: Only users with OWNER role can create a restaurant.");
        }

        restaurant.setOwner(owner);
        return restaurantRepository.save(restaurant);
    }

    // Fetch all restaurants
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    // Fetch a restaurant by ID
    // Change return type from Restaurant to Optional<Restaurant>
    public Optional<Restaurant> getRestaurantById(Long id) {
        return restaurantRepository.findById(id);
    }

    // Fetch restaurants owned by a specific owner
    public List<Restaurant> getRestaurantsByOwner(Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("User with ID " + ownerId + " not found."));
        return restaurantRepository.findByOwner(owner);
    }

    // OPERATION 2: Only the owner of the restaurant can update it.
    public Restaurant updateRestaurant(Long restaurantId, Restaurant updatedRestaurant, Long ownerId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant with ID " + restaurantId + " not found."));

        // Authorization check
        if (!restaurant.getOwner().getId().equals(ownerId)) {
            throw new RuntimeException("Unauthorized: Only the restaurant owner can update it.");
        }

        restaurant.setName(updatedRestaurant.getName());
        restaurant.setAddress(updatedRestaurant.getAddress());
        return restaurantRepository.save(restaurant);
    }

    // OPERATION 3: Only the owner can delete their restaurant.
    public void deleteRestaurant(Long restaurantId, Long ownerId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant with ID " + restaurantId + " not found."));

        // Authorization check
        if (!restaurant.getOwner().getId().equals(ownerId)) {
            throw new RuntimeException("Unauthorized: Only the restaurant owner can delete it.");
        }

        restaurantRepository.deleteById(restaurantId);
    }
}
