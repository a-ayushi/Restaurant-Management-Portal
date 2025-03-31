
package com.restaurant.Restaurant.Management.Portal.service;

import com.restaurant.Restaurant.Management.Portal.model.Restaurant;
import com.restaurant.Restaurant.Management.Portal.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    // Create a new restaurant
    public Restaurant createRestaurant(Restaurant restaurant) {
        //calls restaurantRepository to save the restaurant in DB
        return restaurantRepository.save(restaurant);
    }

    // Fetch all restaurants stored in DB
    public List<Restaurant> getAllRestaurants() {

        //returns a list of all restaurants
        return restaurantRepository.findAll();
    }

    // Fetch a restaurant by userId(ownerId) using the repository method
    public Optional<Restaurant> getRestaurantByUserId(Long userId) {
        return restaurantRepository.findByUserId(userId);
    }
}
