package com.restaurant.Restaurant.Management.Portal.repository;

import com.restaurant.Restaurant.Management.Portal.model.Cart;
import com.restaurant.Restaurant.Management.Portal.model.User;
import com.restaurant.Restaurant.Management.Portal.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    //  Find all cart items for a specific user
    List<Cart> findByUser(User user);

    //  Find all cart items for a specific restaurant
    List<Cart> findByRestaurant(Restaurant restaurant);

    //  Check if a specific menu item is already in the user's cart
    Optional<Cart> findByUserAndMenuItemId(User user, Long menuItemId);
}
