package com.restaurant.Restaurant.Management.Portal.repository;
import com.restaurant.Restaurant.Management.Portal.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    //  Get all order items for a specific order
    List<OrderItem> findByOrderId(Long orderId);
}
