package com.restaurant.Restaurant.Management.Portal.service;

import com.restaurant.Restaurant.Management.Portal.model.Menu;
import com.restaurant.Restaurant.Management.Portal.model.Restaurant;
import com.restaurant.Restaurant.Management.Portal.repository.MenuRepository;
import com.restaurant.Restaurant.Management.Portal.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;


@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    private static final String UPLOAD_DIR = "uploads/"; // directory where uploaded images will be stored


    // Fetch menu by restaurant ID
    public List<Menu> getMenuByRestaurant(Long restaurantId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId); //find the restaurant using restaurantId

        //check if the restaurant exist otherwise throw Exception
        if (restaurant.isPresent()) {
            return menuRepository.findByRestaurant(restaurant.get());
        } else {
            throw new RuntimeException("Restaurant not found");
        }
//        return restaurant.map(menuRepository::findByRestaurant).orElseThrow(() -> new RuntimeException("Restaurant not found"));
    }



    // Add a new menu item
    public Menu addMenuItem(Long restaurantId, String name, double price, MultipartFile imageFile) {
        //only existing restaurants can have menu items
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));//if no restaurant found with given restaurantId

        //  call saveImage( method which uploads image
        String imageUrl = saveImage(imageFile);

        // create a menu object and set its details
        Menu menu = new Menu();
        menu.setName(name);
        menu.setPrice(price);
        menu.setImageUrl(imageUrl);
        menu.setRestaurant(restaurant);

        //menu item saved in DB and return it
        return menuRepository.save(menu);
    }


    // Update menu item
    public Menu updateMenuItem(Long menuId, String name, double price, MultipartFile imageFile) {
        Menu existingMenu = menuRepository.findById(menuId)  //retrieves menu item
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        //updating the name and price
        existingMenu.setName(name);
        existingMenu.setPrice(price);

        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = saveImage(imageFile);
            existingMenu.setImageUrl(imageUrl);
        }
          //save update menu item in db and return it
        return menuRepository.save(existingMenu);
    }


    //delete menu item
    public void deleteMenuItem(Long menuId) {
        if (!menuRepository.existsById(menuId)) { // check if menu item with given Id exists
            throw new RuntimeException("Menu item not found");
        }
        menuRepository.deleteById(menuId);
    }




    //  Helper method to save images
    private String saveImage(MultipartFile imageFile) {
        try {
            if (imageFile == null || imageFile.isEmpty()) {
                return null;
            }

            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(imageFile.getInputStream(), filePath);

            return "/uploads/" + fileName; // Return relative path for frontend
        } catch (IOException e) {
            throw new RuntimeException("Error saving image: " + e.getMessage());
        }
    }


}
