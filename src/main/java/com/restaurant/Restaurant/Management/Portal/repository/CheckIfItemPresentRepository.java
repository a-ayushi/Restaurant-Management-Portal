package com.restaurant.Restaurant.Management.Portal.repository;

import com.restaurant.Restaurant.Management.Portal.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckIfItemPresentRepository  extends JpaRepository<Cart,Long> {
    boolean existsByUserIdAndMenuItemId(Long userId, Long menuItemId);
}
