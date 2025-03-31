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

    //  Add item to cart (if exists, increase quantity)
    public String addToCart(Long userId, Long menuItemId,String itemName,double itemPrice, Long restaurantId, int quantity) {

        //fetch user form db
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        //fetch menu item from db
        Menu menuItem = menuRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

       // fetch restaurant from menu item
        Restaurant restaurant = menuItem.getRestaurant(); // Get restaurant from menu item

        //check if item is already present in cart
        Optional<Cart> existingCartItem = cartRepository.findByUserAndMenuItemId(user, menuItemId);

        if (existingCartItem.isPresent()) {
            Cart cart = existingCartItem.get(); //retrieves the existing cart item
            cart.setQuantity(cart.getQuantity() + 1); // if already in cart then, increase quantity
            cartRepository.save(cart);   //and save to db
            return "Quantity increased for existing cart item.";
        }

        // if not already present then create new cart item
        Cart cartItem = new Cart(menuItem, menuItem.getName(), menuItem.getPrice(), 1, user, restaurant,menuItem.getImageUrl());
        cartRepository.save(cartItem); //save to db
        return "Item added to cart successfully!";
    }

    //  Get all cart items for a user
    public List<Cart> getCartItems(Long userId) {
        User user = userRepository.findById(userId) // retrieves the user object from db
                .orElseThrow(() -> new RuntimeException("User not found"));
        return cartRepository.findByUser(user); //retrieves list of all cart items of given user
    }

    //  Remove an item from the cart
    public void removeCartItem(Long cartItemId) {
        //call deleteById() method of cartRepository to delete cart item from db
        cartRepository.deleteById(cartItemId);
    }

    //  Clear cart for a user ----------- may be used if further enhancements needed
//    public void clearCart(Long userId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        List<Cart> cartItems = cartRepository.findByUser(user);
//        cartRepository.deleteAll(cartItems);
//    }
}
