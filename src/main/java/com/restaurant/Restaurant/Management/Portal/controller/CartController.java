package com.restaurant.Restaurant.Management.Portal.controller;

import com.restaurant.Restaurant.Management.Portal.model.Cart;
import com.restaurant.Restaurant.Management.Portal.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "http://localhost:63342", allowCredentials = "true")
public class CartController {

    @Autowired
    private CartService cartService;

    // ✅ Add an item to the cart
    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addToCart(@RequestBody Map<String, Object> request) {
        Long userId = ((Number) request.get("userId")).longValue();
        Long menuItemId = ((Number) request.get("menuItemId")).longValue();
        String itemName = (String) request.get("itemName");
        double itemPrice = ((Number) request.get("itemPrice")).doubleValue();
        Long restaurantId = ((Number) request.get("restaurantId")).longValue();
        int quantity = request.get("quantity") != null ? ((Number) request.get("quantity")).intValue() : 1;


        String message = cartService.addToCart(userId, menuItemId,itemName,itemPrice,restaurantId,quantity);
        return ResponseEntity.ok(Map.of("message", message));
    }

    // ✅ Get all cart items for a user
    @GetMapping("/{userId}")
    public ResponseEntity<List<Cart>> getCartItems(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartItems(userId));
    }

    // ✅ Remove a specific item from the cart
    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<String> removeCartItem(@PathVariable Long cartItemId) {
        cartService.removeCartItem(cartItemId);
        return ResponseEntity.ok("Item removed from cart.");
    }

    // ✅ Clear the entire cart for a user
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<String> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart cleared successfully.");
    }
}
