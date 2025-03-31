package com.restaurant.Restaurant.Management.Portal.controller;

import com.restaurant.Restaurant.Management.Portal.model.Menu;
import com.restaurant.Restaurant.Management.Portal.service.MenuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import org.springframework.http.MediaType;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;


    // 1. get menu by restaurant ID
    @GetMapping("/{restaurantId}")
    public ResponseEntity<List<Menu>> getMenuByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(menuService.getMenuByRestaurant(restaurantId)); // calls the service layer to fetch the menu
    }

    //  2.  add new menu items
    @PostMapping("/{restaurantId}")
    public ResponseEntity<Menu> addMenuItem(
            @PathVariable Long restaurantId, //extract restaurantId from URL
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam("image") MultipartFile imageFile) {

        //calls the service layer addMenuItem() method
        Menu createdMenu = menuService.addMenuItem(restaurantId, name, price, imageFile);
        return ResponseEntity.ok(createdMenu); // sends back the saved menu item
    }


    //  Update a menu item
    @PutMapping(value = "/{menuId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Menu> updateMenu(
            @PathVariable Long menuId,  //extract menuId from url
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {
        Menu updatedMenu = menuService.updateMenuItem(menuId, name, price, imageFile);//calls the service layer
        return ResponseEntity.ok(updatedMenu);
    }

    //  Delete a menu item
    @DeleteMapping("/{menuId}")
    public ResponseEntity<String> deleteMenuItem(@PathVariable Long menuId) {
        menuService.deleteMenuItem(menuId);//calls the deleteMenuItem method in menuService to delete the item
        return ResponseEntity.ok("Menu item deleted successfully");
    }


}
