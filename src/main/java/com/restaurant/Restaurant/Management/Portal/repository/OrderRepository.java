package com.restaurant.Restaurant.Management.Portal.repository;

import com.restaurant.Restaurant.Management.Portal.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    //retrieves all orders placed by a specific user
    List<Order> findByUserId(Long userId);

    //Retrieves all order for a specific restaurant
    List<Order> findByRestaurantId(Long restaurantId);

    //Retrieves all order with given status
//    List<Order> findByStatus(OrderStatus status);
}
