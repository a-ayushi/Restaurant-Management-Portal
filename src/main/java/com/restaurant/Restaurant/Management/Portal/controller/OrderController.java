package com.restaurant.Restaurant.Management.Portal.controller;
import com.restaurant.Restaurant.Management.Portal.dto.OrderRequestDTO;
import com.restaurant.Restaurant.Management.Portal.model.Order;
import com.restaurant.Restaurant.Management.Portal.model.OrderStatus;
import com.restaurant.Restaurant.Management.Portal.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/orders")

public class OrderController {
    @Autowired
    private OrderService orderService;

    // Place an order
    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequestDTO orderRequest) {
        Order order = orderService.placeOrder(orderRequest);
        return ResponseEntity.ok(order);
    }

    // Get all orders for a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
    }

    // Get all orders for a specific restaurant
    @GetMapping("/restaurant/{restaurantId}")
    public List<Order> getOrdersByRestaurant(@PathVariable Long restaurantId) {
        return orderService.getOrdersByRestaurantId(restaurantId);
    }

    // Get a specific order by ID
    @GetMapping("/{orderId}")
    public Optional<Order> getOrderById(@PathVariable Long orderId) {
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

    // Owner updates order status
    @PutMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long orderId,
                                                    @RequestParam OrderStatus status,
                                                    HttpSession session) {
//        OrderStatus owner = (RestaurantOwner) session.getAttribute("owner");
//        if (owner == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login required");
//        }
        // Get restaurantId from the order
        Order order = orderService.getOrderById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Long restaurantId = order.getRestaurantId();


        Order updated = orderService.updateOrderStatus(orderId, status, restaurantId);
        if (updated!=null) {
            return ResponseEntity.ok("Order status updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized to update this order");
        }
    }

}
