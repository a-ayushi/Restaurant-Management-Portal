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
@RequestMapping("/orders") //base url to handle http request

public class OrderController {
    @Autowired
    private OrderService orderService;

    // 1. Place an order
    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequestDTO orderRequest) {  //orderRequestDTO contains the required info to place order
        Order order = orderService.placeOrder(orderRequest); //call placeOrder() method in OrderService
        return ResponseEntity.ok(order);
    }

    // 2. Get all orders for a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) {
        //returns the list of orders
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId)); //call getOrderByUSerId() method in OrderService to fetch all placed orders by this user
    }

    //3. Get all orders for a specific restaurant
    @GetMapping("/restaurant/{restaurantId}")
    public List<Order> getOrdersByRestaurant(@PathVariable Long restaurantId) {
        //return list of order object
        return orderService.getOrdersByRestaurantId(restaurantId); //call getOrdersByRestaurantId() to fetch all orders placed at this restaurant
    }


    // 4.Get a specific order by ID
    @GetMapping("/{orderId}")
    public Optional<Order> getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

//    // 5. Update order status
//    @PutMapping("/{orderId}")
//    public Order updateOrderStatus(@PathVariable Long orderId, @RequestParam OrderStatus status,@RequestParam Long restaurantOwnerId) { //ensures that only owner of the restaurant associated with order can update status
//        return orderService.updateOrderStatus(orderId, status,restaurantOwnerId);
//    }

    //5. get status of a specific order
    @GetMapping("/{orderId}/status")
    public OrderStatus getOrderStatus(@PathVariable Long orderId, @RequestParam Long userId) { //ensures that only the user who placed the order can check its status
        return orderService.getOrderStatus(orderId, userId);
    }

    //6. APi to cancel the order
    @DeleteMapping("/{orderId}/cancel")
    public String cancelOrder(@PathVariable Long orderId, @RequestParam Long userId) { //ensures that only the user who placed the order can cancel it
        return orderService.cancelOrder(orderId, userId);
    }

    // 7. Owner updates order status
    @PutMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long orderId,
                                                    @RequestParam OrderStatus status,
                                                    HttpSession session) {

//        RestaurantOwner owner = (RestaurantOwner) session.getAttribute("owner");
//        if (owner == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login required");
//        }

        // find the order using orderId
        Order order = orderService.getOrderById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        //get restaurantId from order
        Long restaurantId = order.getRestaurantId();


        //call updateOrderStatus method of order service
        Order updated = orderService.updateOrderStatus(orderId, status, restaurantId); // order exists and the request comes from correct restaurant owner
        if (updated!=null) {
            return ResponseEntity.ok("Order status updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized to update this order");
        }
    }

}
