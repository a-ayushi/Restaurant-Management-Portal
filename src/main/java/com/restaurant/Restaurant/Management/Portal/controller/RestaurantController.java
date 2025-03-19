package com.restaurant.Restaurant.Management.Portal.controller;


import com.restaurant.Restaurant.Management.Portal.model.Restaurant;
import com.restaurant.Restaurant.Management.Portal.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    // Total 5 APIs used for RestaurantController

    //1. POST /restaurants-> Only logged-in owners can create a restaurant.
    @PostMapping
    public Restaurant createRestaurant(@RequestBody Restaurant restaurant, HttpSession session) {
        Long ownerId = (Long) session.getAttribute("userId");
        if (ownerId == null) {
            throw new RuntimeException("User not logged in.");
        }
        return restaurantService.createRestaurant(restaurant, ownerId);
    }

    //2. GET /restaurants-> Anyone can view all restaurants.
    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    //3. GET /restaurant/{id}-> Fetch a specific restaurant by ID.
    @GetMapping("/{id}")
    public Optional<Restaurant> getRestaurantById(@PathVariable Long id) {
        return restaurantService.getRestaurantById(id);
    }

    //4. PUT /restaurants/{id} ->  Only the owner of that restaurant can update it.
    @PutMapping("/{id}")
    public Restaurant updateRestaurant(@PathVariable Long id, @RequestBody Restaurant restaurant, HttpSession session) {
        Long ownerId = (Long) session.getAttribute("userId");
        if (ownerId == null) {
            throw new RuntimeException("User not logged in.");
        }
        return restaurantService.updateRestaurant(id, restaurant, ownerId);
    }

    //5. DELETE /restaurants/{id} ->   Only the owner of that restaurant can delete it.
    @DeleteMapping("/{id}")
    public String deleteRestaurant(@PathVariable Long id, HttpSession session) {
        Long ownerId = (Long) session.getAttribute("userId");
        if (ownerId == null) {
            throw new RuntimeException("User not logged in.");
        }
        restaurantService.deleteRestaurant(id, ownerId);
        return "Restaurant deleted successfully!";
    }


}
