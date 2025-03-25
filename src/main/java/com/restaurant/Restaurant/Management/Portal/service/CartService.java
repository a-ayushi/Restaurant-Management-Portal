package com.restaurant.Restaurant.Management.Portal.service;

import com.restaurant.Restaurant.Management.Portal.model.Cart;
import com.restaurant.Restaurant.Management.Portal.model.Menu;
import com.restaurant.Restaurant.Management.Portal.model.User;
import com.restaurant.Restaurant.Management.Portal.model.Restaurant;
import com.restaurant.Restaurant.Management.Portal.repository.CartRepository;
import com.restaurant.Restaurant.Management.Portal.repository.MenuRepository;
import com.restaurant.Restaurant.Management.Portal.repository.UserRepository;
import com.restaurant.Restaurant.Management.Portal.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    // ✅ Add item to cart (if exists, increase quantity)
    public String addToCart(Long userId, Long menuItemId,String itemName,double itemPrice, Long restaurantId, int quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Menu menuItem = menuRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));
        Restaurant restaurant = menuItem.getRestaurant(); // Get restaurant from menu item

        Optional<Cart> existingCartItem = cartRepository.findByUserAndMenuItemId(user, menuItemId);

        if (existingCartItem.isPresent()) {
            Cart cart = existingCartItem.get();
            cart.setQuantity(cart.getQuantity() + 1); // Increase quantity
            cartRepository.save(cart);
            return "Quantity increased for existing cart item.";
        }

        // New cart item
        Cart cartItem = new Cart(menuItem, menuItem.getName(), menuItem.getPrice(), 1, user, restaurant,menuItem.getImageUrl());
        cartRepository.save(cartItem);
        return "Item added to cart successfully!";
    }

    // ✅ Get all cart items for a user
    public List<Cart> getCartItems(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return cartRepository.findByUser(user);
    }

    // ✅ Remove an item from the cart
    public void removeCartItem(Long cartItemId) {
        cartRepository.deleteById(cartItemId);
    }

    // ✅ Clear cart for a user
    public void clearCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Cart> cartItems = cartRepository.findByUser(user);
        cartRepository.deleteAll(cartItems);
    }
}
