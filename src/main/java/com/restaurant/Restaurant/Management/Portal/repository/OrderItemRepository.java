package com.restaurant.Restaurant.Management.Portal.repository;

import com.restaurant.Restaurant.Management.Portal.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    //retrieves all order items associated with specific order
    //helps in fetching detailed order contents
    List<OrderItem> findByOrderId(Long orderId);
}
