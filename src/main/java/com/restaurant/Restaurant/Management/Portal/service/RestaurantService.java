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



//   OPERATION 1 : Only an OWNER can create a restaurant.
public Restaurant createRestaurant(Restaurant restaurant, Long ownerId) {
    Optional<User> owner = userRepository.findById(ownerId);

    if (owner.isPresent() && "OWNER".equals(owner.get().getRole())) {
        restaurant.setOwner(owner.get()); // Set the owner correctly
        return restaurantRepository.save(restaurant);
    } else {
        throw new RuntimeException("Only an OWNER can create a restaurant.");
    }
}

//    Fetches all restaurants.
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }
//Finds a restaurant by ID.
    public Optional<Restaurant> getRestaurantById(Long id) {
        return restaurantRepository.findById(id);
    }
//Returns restaurants belonging to a specific owner.
public List<Restaurant> getRestaurantsByOwner(Long ownerId) {
    Optional<User> owner = userRepository.findById(ownerId);
    if (owner.isEmpty()) {
        throw new RuntimeException("Owner not found.");
    }
    return restaurantRepository.findByOwner(owner.get());
}


// OPERATION 2: Only the owner of the restaurant can update it.
public Restaurant updateRestaurant(Long restaurantId, Restaurant updatedRestaurant, Long ownerId) {
    Optional<Restaurant> existingRestaurant = restaurantRepository.findById(restaurantId);

    if (existingRestaurant.isPresent()){  // not checking the condition here , so that an exception can be thrown
        Restaurant restaurant = existingRestaurant.get();

        // Check if the logged-in user is the owner of the restaurant

        if (restaurant.getOwner() == null || !restaurant.getOwner().getId().equals(ownerId)) {
            throw new RuntimeException("You are not allowed to update this restaurant.");
        }
        restaurant.setName(updatedRestaurant.getName());
        restaurant.setAddress(updatedRestaurant.getAddress());
        return restaurantRepository.save(restaurant);
    } else {
        throw new RuntimeException("Restaurant NOT found!");
    }
}

    //OPERATIONS 3: Only the owner can delete their restaurant.
    public void deleteRestaurant(Long restaurantId, Long ownerId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);

        if (restaurant.isPresent()){
            // Check if the logged-in user is the owner of the restaurant
            if (restaurant.get().getOwner() == null || !restaurant.get().getOwner().getId().equals(ownerId)) {
                throw new RuntimeException("You are not allowed to delete this restaurant.");
            }
            restaurantRepository.deleteById(restaurantId);
        } else {
            throw new RuntimeException("Restaurant NOT found!");
        }
    }
}
