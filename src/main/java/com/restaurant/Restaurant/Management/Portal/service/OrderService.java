package com.restaurant.Restaurant.Management.Portal.service;

import com.restaurant.Restaurant.Management.Portal.dto.OrderItemDTO;
import com.restaurant.Restaurant.Management.Portal.dto.OrderRequestDTO;
import com.restaurant.Restaurant.Management.Portal.model.OrderItem;
import com.restaurant.Restaurant.Management.Portal.model.Menu;
import com.restaurant.Restaurant.Management.Portal.model.Order;
import com.restaurant.Restaurant.Management.Portal.model.OrderStatus;
import com.restaurant.Restaurant.Management.Portal.repository.MenuRepository;
import com.restaurant.Restaurant.Management.Portal.repository.OrderItemRepository;
import com.restaurant.Restaurant.Management.Portal.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository; // Repository for OrderItems

    @Autowired
    private MenuRepository menuRepository; // Fetch menu items


//     Place an Order with Multiple Items

    public Order placeOrder(OrderRequestDTO orderRequest) {
        if (orderRequest.getOrderItems().isEmpty()) {
            throw new RuntimeException("Cannot place an empty order.");
        }

        // Calculate total price
        double totalPrice = orderRequest.getOrderItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        // Create the Order entity
        Order order = new Order(orderRequest.getUserId(), orderRequest.getRestaurantId(), totalPrice);
        order.setStatus(OrderStatus.PENDING);
        order = orderRepository.save(order); // Save Order First

        // Loop through each item in the order request
        for (OrderItemDTO itemDTO : orderRequest.getOrderItems()) {
            Menu menuItem = menuRepository.findById(itemDTO.getMenuItemId())
                    .orElseThrow(() -> new RuntimeException("Menu item not found"));

            OrderItem orderItem = new OrderItem(order, menuItem, itemDTO.getQuantity(), itemDTO.getPrice());
            orderItemRepository.save(orderItem);
        }

        return order;
    }

    // Get Order Status (Only the user who placed it
    public OrderStatus getOrderStatus(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        // Ensure the correct user is requesting status
        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException("You can only track your own orders.");
        }

        return order.getStatus();
    }


     // Cancel an Order (Only if it's still pending)

    public String cancelOrder(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        // Ensure the correct user is cancelling the order
        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException("You can only cancel your own orders.");
        }

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Only pending orders can be cancelled.");
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        return "Order has been successfully cancelled.";
    }


    //  Get All Orders for a Specific User
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }


     // Get All Orders for a Specific Restaurant

    public List<Order> getOrdersByRestaurantId(Long restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId);
    }


    // Update Order Status (Handled by Restaurant Owner)

    public Order updateOrderStatus(Long orderId, OrderStatus status, Long restaurantOwnerId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        // Ensure the restaurant owner manages this restaurant's orders
        if (!order.getRestaurantId().equals(restaurantOwnerId)) {
            throw new RuntimeException("You can only manage orders for your restaurant.");
        }

        if (order.getStatus() == OrderStatus.DELIVERED || order.getStatus() == OrderStatus.CANCELLED) {
            throw new RuntimeException("Cannot update a completed or cancelled order.");
        }

        // Allow cancellation only if the order is still pending
        if (status == OrderStatus.CANCELLED && order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Orders can only be cancelled while still pending.");
        }

        order.setStatus(status);
        return orderRepository.save(order);
    }


    // Get Order by ID
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
    }
}
