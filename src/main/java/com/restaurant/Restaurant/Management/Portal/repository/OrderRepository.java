package com.restaurant.Restaurant.Management.Portal.repository;

import com.restaurant.Restaurant.Management.Portal.model.Order;
import com.restaurant.Restaurant.Management.Portal.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    List<Order> findByRestaurantId(Long restaurantId);
    List<Order> findByStatus(OrderStatus status);
}
