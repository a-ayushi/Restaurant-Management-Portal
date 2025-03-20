package com.restaurant.Restaurant.Management.Portal.controller;


import com.restaurant.Restaurant.Management.Portal.model.MenuItem;
import com.restaurant.Restaurant.Management.Portal.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequestMapping("/menu")
public class MenuItemController {
    @Autowired
    private MenuItemService menuItemService;

    // 1. Add a new menu item (Only Owners)
    @PostMapping("/{restaurantId}")
    public ResponseEntity<?> addMenuItem(@PathVariable Long restaurantId, @RequestBody MenuItem menuItem, HttpSession session) {
        Long ownerId = (Long) session.getAttribute("userId");
        if (ownerId == null) {
            return ResponseEntity.status(401).body("{\"error\": \"User not logged in.\"}");
        }
        MenuItem createdItem = menuItemService.addMenuItem(restaurantId, menuItem, ownerId);
        return ResponseEntity.ok(createdItem);
    }

    // 2. Get all menu items for a specific restaurant
    @GetMapping("/{restaurantId}")
    public ResponseEntity<List<MenuItem>> getMenuItemsByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(menuItemService.getMenuItemsByRestaurant(restaurantId));
    }

    // 3. Update a menu item (Only Owners)
    @PutMapping("/{menuItemId}")
    public ResponseEntity<?> updateMenuItem(@PathVariable Long menuItemId, @RequestBody MenuItem menuItem, HttpSession session) {
        Long ownerId = (Long) session.getAttribute("userId");
        if (ownerId == null) {
            return ResponseEntity.status(401).body("{\"error\": \"User not logged in.\"}");
        }
        MenuItem updatedItem = menuItemService.updateMenuItem(menuItemId, menuItem, ownerId);
        return ResponseEntity.ok(updatedItem);
    }

    // 4. Delete a menu item (Only Owners)
    @DeleteMapping("/{menuItemId}")
    public ResponseEntity<?> deleteMenuItem(@PathVariable Long menuItemId, HttpSession session) {
        Long ownerId = (Long) session.getAttribute("userId");
        if (ownerId == null) {
            return ResponseEntity.status(401).body("{\"error\": \"User not logged in.\"}");
        }
        menuItemService.deleteMenuItem(menuItemId, ownerId);
        return ResponseEntity.ok("{\"message\": \"Menu item deleted successfully!\"}");
    }
}
