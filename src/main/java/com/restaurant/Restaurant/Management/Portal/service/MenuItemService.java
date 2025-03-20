package com.restaurant.Restaurant.Management.Portal.service;

import com.restaurant.Restaurant.Management.Portal.model.MenuItem;
import com.restaurant.Restaurant.Management.Portal.model.Restaurant;
import com.restaurant.Restaurant.Management.Portal.repository.MenuItemRepository;
import com.restaurant.Restaurant.Management.Portal.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    // Create a new menu item (Only by restaurant owner)
    public MenuItem addMenuItem(Long restaurantId, MenuItem menuItem, Long ownerId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);

        if (restaurant.isPresent() && restaurant.get().getOwner().getId().equals(ownerId)) {
            menuItem.setRestaurant(restaurant.get());
            return menuItemRepository.save(menuItem);
        } else {
            throw new RuntimeException("Only the restaurant owner can add menu items.");
        }
    }

    // Get all menu items for a specific restaurant
    public List<MenuItem> getMenuItemsByRestaurant(Long restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId);
    }

    // Update a menu item (Only by restaurant owner)
    public MenuItem updateMenuItem(Long menuItemId, MenuItem updatedItem, Long ownerId) {
        Optional<MenuItem> existingItem = menuItemRepository.findById(menuItemId);

        if (existingItem.isPresent() && existingItem.get().getRestaurant().getOwner().getId().equals(ownerId)) {
            MenuItem menuItem = existingItem.get();
            menuItem.setName(updatedItem.getName());
            menuItem.setPrice(updatedItem.getPrice());
            return menuItemRepository.save(menuItem);
        } else {
            throw new RuntimeException("Only the restaurant owner can update menu items.");
        }
    }

    // Delete a menu item (Only by restaurant owner)
    public void deleteMenuItem(Long menuItemId, Long ownerId) {
        Optional<MenuItem> menuItem = menuItemRepository.findById(menuItemId);

        if (menuItem.isPresent() && menuItem.get().getRestaurant().getOwner().getId().equals(ownerId)) {
            menuItemRepository.deleteById(menuItemId);
        } else {
            throw new RuntimeException("Only the restaurant owner can delete menu items.");
        }
    }
}
