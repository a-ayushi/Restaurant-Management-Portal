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

    //  1. CREATE a restaurant
    @PostMapping( consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Restaurant> createRestaurant(  // send back a restaurant object
            @RequestParam("name") String name,  // extract values from form data
            @RequestParam("address") String address,
            @RequestParam("ownerId") Long ownerId,
            @RequestParam("image") MultipartFile imageFile
    ) {
        // Save image and create restaurant object
        //calls the saveImage method to store image and get its path
        String imageUrl = saveImage(imageFile);

        Restaurant restaurant = new Restaurant(); // creating instance of Restaurant class

        //sets the properties of restaurant object
        //groups together all details to save in db
        restaurant.setName(name);
        restaurant.setAddress(address);
        restaurant.setOwnerId(ownerId);
        restaurant.setImageUrl(imageUrl);

        // stores saved restaurant with generated id
        Restaurant saved = restaurantService.createRestaurant(restaurant);
        return ResponseEntity.ok(saved);
    }


    //  2. GET all restaurants
    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        //calls service layer method to get the list of  restaurants from db
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

  
    // 3. GET a restaurant by User ID
     @GetMapping("/user/{userId}")
     public ResponseEntity<?> getRestaurantByUserId(@PathVariable Long userId) {
        // calls getRestaurantByUserId method to check if restaurant for given userId is present
      Optional<Restaurant> restaurant = restaurantService.getRestaurantByUserId(userId);
      if (restaurant.isPresent()) {
          return ResponseEntity.ok(restaurant.get());
      } else {
          return ResponseEntity.status(404).body("Restaurant not found for user id " + userId);
      }
  }



  // method to save an uploaded image to uploads/ directory and returns the path
    private String saveImage(MultipartFile imageFile) {
        try {
            String uploadsDir = "uploads/";  //folder where images will be stored
            String originalFilename = imageFile.getOriginalFilename();
            String fileName = System.currentTimeMillis() + "_" + originalFilename;// filename is unique
            //create the file path
            Path path = Paths.get(uploadsDir + fileName);

            //created uploads/ folder if it doesn't exist
            Files.createDirectories(path.getParent());
            Files.write(path, imageFile.getBytes()); //write the image bytes to file system

            return "/uploads/" + fileName; // Return  path
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
            }
    }

}
  
  

