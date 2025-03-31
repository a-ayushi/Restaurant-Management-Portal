package com.restaurant.Restaurant.Management.Portal.controller;

import com.restaurant.Restaurant.Management.Portal.model.Cart;
import com.restaurant.Restaurant.Management.Portal.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "http://localhost:63342", allowCredentials = "true")
public class CartController {

    @Autowired
    private CartService cartService;

    // 1. Add an item to the cart
    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addToCart(@RequestBody Map<String, Object> request) {
        Long userId = Long.parseLong(request.get("userId").toString());
        Long menuItemId = Long.parseLong(request.get("menuItemId").toString());
        String itemName = request.get("itemName").toString();
        double itemPrice = Double.parseDouble(request.get("itemPrice").toString());
        Long restaurantId = Long.parseLong(request.get("restaurantId").toString());
        int quantity = request.get("quantity") != null ? Integer.parseInt(request.get("quantity").toString()) : 1;//check if quantity is present ,if not then assign default as 1.

       //call addToCart method from cartService and pass extracted values
        String message = cartService.addToCart(userId, menuItemId,itemName,itemPrice,restaurantId,quantity);
        return ResponseEntity.ok(Map.of("message", message)); // return a string message
    }

    // 2. Get all cart items for a user
    @GetMapping("/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getCartItems(@PathVariable Long userId) {  //method return list of maps
        List<Cart> cartItems = cartService.getCartItems(userId);

        // Convert Cart objects into a structured JSON response with imageUrl
        List<Map<String, Object>> response = cartItems.stream().map(cart -> Map.of(  //for each cart item it creates a map.
                "id", cart.getId(),
                "menuItemId", cart.getMenuItem().getId(),
                "itemName", cart.getItemName(),
                "itemPrice", cart.getItemPrice(),
                "quantity", cart.getQuantity(),
                "restaurant", Map.of(   // it is a nested map
                        "id", cart.getRestaurant().getId(),
                        "name", cart.getRestaurant().getName()
                ),
                "imageUrl", cart.getMenuItem().getImageUrl() //  Include image URL
        )).collect(Collectors.toList()); //collects all mapped cart items into list of map<string,object>

        return ResponseEntity.ok(response); //return list of maps
    }

    // 3. Remove a specific item from the cart
    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<String> removeCartItem(@PathVariable Long cartItemId) {
        cartService.removeCartItem(cartItemId);  //calls the service layer
        return ResponseEntity.ok("Item removed from cart."); //returns a success message
    }


    //commenting for now, but can be used in future
//    // API to Clear the entire cart for a user
//    @DeleteMapping("/clear/{userId}")
//    public ResponseEntity<String> clearCart(@PathVariable Long userId) {
//        cartService.clearCart(userId);
//        return ResponseEntity.ok("Cart cleared successfully.");
//    }
}
