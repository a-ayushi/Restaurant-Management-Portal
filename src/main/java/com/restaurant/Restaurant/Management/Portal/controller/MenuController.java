package com.restaurant.Restaurant.Management.Portal.controller;

import com.restaurant.Restaurant.Management.Portal.model.Menu;
import com.restaurant.Restaurant.Management.Portal.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

    // 1. GET menu by restaurant ID
    @GetMapping("/{restaurantId}")
    public ResponseEntity<List<Menu>> getMenuByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(menuService.getMenuByRestaurant(restaurantId));
    }

    //  2. POST add new menu item
    // add image also
    @PostMapping("/{restaurantId}")
    public ResponseEntity<Menu> addMenuItem(
            @PathVariable Long restaurantId,
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam("image") MultipartFile imageFile) {

        Menu createdMenu = menuService.addMenuItem(restaurantId, name, price, imageFile);
        return ResponseEntity.ok(createdMenu);
    }



    //  Update a menu item
    @PutMapping("/{menuId}")
    public ResponseEntity<Menu> updateMenuItem(@PathVariable Long menuId, @RequestBody Menu menu) {
        return ResponseEntity.ok(menuService.updateMenuItem(menuId, menu));
    }

    //  Delete a menu item
    @DeleteMapping("/{menuId}")
    public ResponseEntity<String> deleteMenuItem(@PathVariable Long menuId) {
        menuService.deleteMenuItem(menuId);
        return ResponseEntity.ok("Menu item deleted successfully");
    }


}
