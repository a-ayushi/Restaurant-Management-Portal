package com.restaurant.Restaurant.Management.Portal.service;
import com.restaurant.Restaurant.Management.Portal.model.CartItem;
import com.restaurant.Restaurant.Management.Portal.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;

    // Add an item to the cart
    public CartItem addToCart(Long userId, Long menuItemId, int quantity) {
        CartItem cartItem = new CartItem(userId, menuItemId, quantity);
        return cartItemRepository.save(cartItem);
    }

    // Get all cart items for a specific user
    public List<CartItem> getCartItems(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }

    // Update cart item quantity (Only if it belongs to the user)
    public CartItem updateCartItem(Long userId, Long cartItemId, int newQuantity) {
        Optional<CartItem> cartItemOpt = cartItemRepository.findById(cartItemId);

        if (cartItemOpt.isPresent()) {
            CartItem cartItem = cartItemOpt.get();
            if (!cartItem.getUserId().equals(userId)) {
                throw new RuntimeException("Unauthorized: You cannot modify this cart item.");
            }
            cartItem.setQuantity(newQuantity);
            return cartItemRepository.save(cartItem);
        } else {
            throw new RuntimeException("Cart item not found");
        }
    }

    // Remove a cart item (Only if it belongs to the user)
    public void removeCartItem(Long userId, Long cartItemId) {
        Optional<CartItem> cartItemOpt = cartItemRepository.findById(cartItemId);

        if (cartItemOpt.isPresent()) {
            CartItem cartItem = cartItemOpt.get();
            if (!cartItem.getUserId().equals(userId)) {
                throw new RuntimeException("Unauthorized: You cannot remove this cart item.");
            }
            cartItemRepository.deleteById(cartItemId);
        } else {
            throw new RuntimeException("Cart item not found");
        }
    }
}
