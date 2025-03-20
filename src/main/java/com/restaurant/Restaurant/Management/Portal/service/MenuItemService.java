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
        return restaurantRepository.findById(restaurantId).filter(restaurant -> restaurant.getOwner().getId().equals(ownerId)) // Check ownership
                .map(restaurant -> {
                    menuItem.setRestaurant(restaurant);
                    return menuItemRepository.save(menuItem);
                }).orElseThrow(() -> new RuntimeException("Only the restaurant owner can add menu items."));
    }


    // Get all menu items for a specific restaurant
    public List<MenuItem> getMenuItemsByRestaurant(Long restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId);
    }

    // Update a menu item (Only by restaurant owner)
    public MenuItem updateMenuItem(Long menuItemId, MenuItem updatedItem, Long ownerId) {
        return menuItemRepository.findById(menuItemId).filter(menuItem -> menuItem.getRestaurant().getOwner().getId().equals(ownerId)) // Check ownership
                .map(menuItem -> {
                    menuItem.setName(updatedItem.getName());
                    menuItem.setPrice(updatedItem.getPrice());
                    return menuItemRepository.save(menuItem);
                }).orElseThrow(() -> new RuntimeException("Only the restaurant owner can update this menu item."));
    }


    // Delete a menu item (Only by restaurant owner)
    public void deleteMenuItem(Long menuItemId, Long ownerId) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId).filter(item -> item.getRestaurant().getOwner().getId().equals(ownerId)) // Check ownership
                .orElseThrow(() -> new RuntimeException("Only the restaurant owner can delete this menu item."));

        menuItemRepository.delete(menuItem);
    }
}
