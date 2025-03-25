package com.restaurant.Restaurant.Management.Portal.service;


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

      // Place an order
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository; // New Repository for OrderItems

    @Autowired
    private MenuRepository menuRepository; // To fetch menu items


    public Order placeOrder(Order orderRequest) {
        if (orderRequest.getOrderItems().isEmpty()) {
            throw new RuntimeException("Cannot place an empty order.");
        }

        // Calculate total price
        double totalPrice = orderRequest.getOrderItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        // Create order
        Order order = new Order(orderRequest.getUserId(), orderRequest.getRestaurantId(), totalPrice);
        order.setStatus(OrderStatus.PENDING);
        order = orderRepository.save(order); // Save Order First

        // Create order items & save
        for (OrderItemDTO itemDTO : orderRequest.getOrderItems()) {
            Menu menuItem = menuRepository.findById(itemDTO.getMenuItemId())
                    .orElseThrow(() -> new RuntimeException("Menu item not found"));

            OrderItem orderItem = new OrderItem(order, menuItem, itemDTO.getQuantity(), itemDTO.getPrice());
            orderItemRepository.save(orderItem);
        }

        return order;
    }

//    public Order placeOrder(Order order) {
//        order.setStatus(OrderStatus.valueOf("PENDING")); // Default status when placing an order
//        return orderRepository.save(order);
//    }


    // Get order status (Only the user who placed the order can check)
    public OrderStatus getOrderStatus(Long orderId, Long userId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            // Ensure only the customer who placed the order can check the status
            if (!order.getUserId().equals(userId)) {
                throw new RuntimeException("You can only track your own orders.");
            }

            return order.getStatus(); // Return order status
        } else {
            throw new RuntimeException("Order not found with ID: " + orderId);
        }
    }

    // Cancel an order (Only if it is still pending)
    public String cancelOrder(Long orderId, Long userId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            // Ensure only the customer who placed the order can cancel it
            if (!order.getUserId().equals(userId)) {
                throw new RuntimeException("You can only cancel your own orders.");
            }

            // Only allow cancellation if the order is still pending
            if (order.getStatus() != OrderStatus.PENDING) {
                throw new RuntimeException("Only pending orders can be cancelled.");
            }

            // Update status to CANCELLED
            order.setStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);
            return "Order has been successfully cancelled.";
        } else {
            throw new RuntimeException("Order not found with ID: " + orderId);
        }
    }


    // Get all orders for a specific user
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    // Get all orders for a specific restaurant
    public List<Order> getOrdersByRestaurantId(Long restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId);
    }

    // Update order status (Pending -> Preparing -> Completed -> Cancelled)
    public Order updateOrderStatus(Long orderId, OrderStatus status, Long restaurantOwnerId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);

        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();

            // Ensure the restaurant owner is managing their own restaurant's orders
            if (!order.getRestaurantId().equals(restaurantOwnerId)) {
                throw new RuntimeException("You can only manage orders for your restaurant.");
            }
            // Prevent status changes for completed or cancelled orders
            if (order.getStatus() == OrderStatus.COMPLETED) {
                throw new RuntimeException("Cannot update a completed order.");
            }
            if (order.getStatus() == OrderStatus.CANCELLED) {
                throw new RuntimeException("Cannot update a cancelled order.");
            }

            // Allow cancellation only if the order is still pending
            if (status == OrderStatus.CANCELLED && order.getStatus() != OrderStatus.PENDING) {
                throw new RuntimeException("Orders can only be cancelled while still pending.");
            }

            // Update the order status
            order.setStatus(status);
            return orderRepository.save(order);
        }else {
            throw new RuntimeException("Order not found with ID: " + orderId);
        }
    }

    // Get order by ID
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
    }
}
