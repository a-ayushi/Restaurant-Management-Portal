//package com.restaurant.Restaurant.Management.Portal.controller;
//import com.restaurant.Restaurant.Management.Portal.model.CartItem;
//import com.restaurant.Restaurant.Management.Portal.service.CartItemService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import jakarta.servlet.http.HttpSession;
//import java.util.List;
//
//@RestController
//@RequestMapping("/cart")
//public class CartItemController {
//
//    @Autowired
//    private CartItemService cartItemService;
//
//    // 1️⃣ Add an item to the cart
//    @PostMapping
//    public ResponseEntity<?> addToCart(@RequestBody CartItem cartItem, HttpSession session) {
//        Long userId = (Long) session.getAttribute("userId");
//        if (userId == null) {
//            return ResponseEntity.status(401).body("{\"error\": \"User not logged in.\"}");
//        }
//        CartItem addedItem = cartItemService.addToCart(userId, cartItem.getMenuItemId(), cartItem.getQuantity());
//        return ResponseEntity.ok(addedItem);
//    }
//
//    // 2️⃣ Get all cart items for the logged-in user
//    @GetMapping
//    public ResponseEntity<?> getCartItems(HttpSession session) {
//        Long userId = (Long) session.getAttribute("userId");
//        if (userId == null) {
//            return ResponseEntity.status(401).body("{\"error\": \"User not logged in.\"}");
//        }
//        List<CartItem> cartItems = cartItemService.getCartItems(userId);
//        return ResponseEntity.ok(cartItems);
//    }
//
//    // 3️⃣ Update cart item quantity (only if it belongs to the logged-in user)
//    @PutMapping("/{cartItemId}")
//    public ResponseEntity<?> updateCartItem(@PathVariable Long cartItemId, @RequestParam int quantity, HttpSession session) {
//        Long userId = (Long) session.getAttribute("userId");
//        if (userId == null) {
//            return ResponseEntity.status(401).body("{\"error\": \"User not logged in.\"}");
//        }
//        try {
//            CartItem updatedItem = cartItemService.updateCartItem(userId, cartItemId, quantity);
//            return ResponseEntity.ok(updatedItem);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(403).body("{\"error\": \"" + e.getMessage() + "\"}");
//        }
//    }
//
//    // 4️⃣ Remove an item from the cart (only if it belongs to the logged-in user)
//    @DeleteMapping("/{cartItemId}")
//    public ResponseEntity<?> removeCartItem(@PathVariable Long cartItemId, HttpSession session) {
//        Long userId = (Long) session.getAttribute("userId");
//        if (userId == null) {
//            return ResponseEntity.status(401).body("{\"error\": \"User not logged in.\"}");
//        }
//        try {
//            cartItemService.removeCartItem(userId, cartItemId);
//            return ResponseEntity.ok("{\"message\": \"Cart item removed successfully!\"}");
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(403).body("{\"error\": \"" + e.getMessage() + "\"}");
//        }
//    }
//
//
//}
