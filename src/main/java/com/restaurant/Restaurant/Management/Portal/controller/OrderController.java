package com.restaurant.Restaurant.Management.Portal.controller;
import com.restaurant.Restaurant.Management.Portal.model.Order;
import com.restaurant.Restaurant.Management.Portal.model.OrderStatus;
import com.restaurant.Restaurant.Management.Portal.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")

public class OrderController {
    @Autowired
    private OrderService orderService;

    // Place an order
    @PostMapping
    public Order placeOrder(@RequestBody Order order) {
        return orderService.placeOrder(order);
    }

    // Get all orders for a specific user
    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUser(@PathVariable Long userId) {
        return orderService.getOrdersByUserId(userId);
    }

    // Get all orders for a specific restaurant
    @GetMapping("/restaurant/{restaurantId}")
    public List<Order> getOrdersByRestaurant(@PathVariable Long restaurantId) {
        return orderService.getOrdersByRestaurantId(restaurantId);
    }

    // Get a specific order by ID
    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    // Update order status
    @PutMapping("/{orderId}")
    public Order updateOrderStatus(@PathVariable Long orderId, @RequestParam OrderStatus status,@RequestParam Long restaurantOwnerId) {
        return orderService.updateOrderStatus(orderId, status,restaurantOwnerId);
    }

    @GetMapping("/{orderId}/status")
    public OrderStatus getOrderStatus(@PathVariable Long orderId, @RequestParam Long userId) {
        return orderService.getOrderStatus(orderId, userId);
    }

    //APi to cancel the order
    @DeleteMapping("/{orderId}/cancel")
    public String cancelOrder(@PathVariable Long orderId, @RequestParam Long userId) {
        return orderService.cancelOrder(orderId, userId);
    }

}
