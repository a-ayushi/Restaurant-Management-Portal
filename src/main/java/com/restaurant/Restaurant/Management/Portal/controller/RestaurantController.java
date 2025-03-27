package com.restaurant.Restaurant.Management.Portal.controller;

import com.restaurant.Restaurant.Management.Portal.model.Restaurant;
import com.restaurant.Restaurant.Management.Portal.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;


import org.springframework.http.MediaType;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    //  1. CREATE a restaurant (No authentication required)
    @PostMapping( consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Restaurant> createRestaurant(
            @RequestParam("name") String name,
            @RequestParam("address") String address,
            @RequestParam("ownerId") Long ownerId,
            @RequestParam("image") MultipartFile imageFile
    ) {
        // Save image and create restaurant object
        String imageUrl = saveImage(imageFile); // implement this

        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setAddress(address);
        restaurant.setOwnerId(ownerId);
        restaurant.setImageUrl(imageUrl);

        Restaurant saved = restaurantService.createRestaurant(restaurant);
        return ResponseEntity.ok(saved);
    }




//    @PostMapping
//    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
//        Restaurant createdRestaurant = restaurantService.createRestaurant(restaurant);
//        return ResponseEntity.ok(createdRestaurant);
//    }

    //  2. GET all restaurants
    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }
  
  //  3. GET a restaurant by User ID
  @GetMapping("/user/{userId}")
  public ResponseEntity<?> getRestaurantByUserId(@PathVariable Long userId) {
      Optional<Restaurant> restaurant = restaurantService.getRestaurantByUserId(userId);
      if (restaurant.isPresent()) {
          return ResponseEntity.ok(restaurant.get());
      } else {
          return ResponseEntity.status(404).body("Restaurant not found for user id " + userId);
      }
  }


    private String saveImage(MultipartFile imageFile) {
        try {
            String uploadsDir = "uploads/";
            String originalFilename = imageFile.getOriginalFilename();
            String fileName = System.currentTimeMillis() + "_" + originalFilename;
            Path path = Paths.get(uploadsDir + fileName);

            Files.createDirectories(path.getParent());
            Files.write(path, imageFile.getBytes());

            return "/uploads/" + fileName; // Return URL or path
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
            }
    }

}
  
  

