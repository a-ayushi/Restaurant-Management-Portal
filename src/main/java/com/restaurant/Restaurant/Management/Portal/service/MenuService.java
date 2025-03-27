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

    private static final String UPLOAD_DIR = "uploads/";


    // Fetch menu by restaurant ID
    public List<Menu> getMenuByRestaurant(Long restaurantId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        return restaurant.map(menuRepository::findByRestaurant).orElseThrow(() -> new RuntimeException("Restaurant not found"));
    }


    // Add a new menu item with an image
    public Menu addMenuItem(Long restaurantId, String name, double price, MultipartFile imageFile) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        String imageUrl = saveImage(imageFile);

        Menu menu = new Menu();
        menu.setName(name);
        menu.setPrice(price);
        menu.setImageUrl(imageUrl);
        menu.setRestaurant(restaurant);

        return menuRepository.save(menu);
    }

//    // Add a new menu item
//    public Menu addMenuItem(Long restaurantId, Menu menu) {
//        Restaurant restaurant = restaurantRepository.findById(restaurantId)
//                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
//
//        menu.setRestaurant(restaurant);
//        return menuRepository.save(menu);
//    }

    //update menu item
    public Menu updateMenuItem(Long menuId, Menu updatedMenu) {
        Menu existingMenu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        existingMenu.setName(updatedMenu.getName());
        existingMenu.setPrice(updatedMenu.getPrice());

        return menuRepository.save(existingMenu);
    }

    //delete menu item
    public void deleteMenuItem(Long menuId) {
        if (!menuRepository.existsById(menuId)) {
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
