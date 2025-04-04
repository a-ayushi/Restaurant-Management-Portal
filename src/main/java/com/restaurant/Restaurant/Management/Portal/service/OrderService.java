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


    // Place an Order with Multiple Items
    public Order placeOrder(OrderRequestDTO orderRequest) { //receive order related data from frontend
        if (orderRequest.getOrderItems().isEmpty()) {  //check if order is empty
            throw new RuntimeException("Cannot place an empty order.");
        }

        // Calculate total price
        double totalPrice = orderRequest.getOrderItems().stream()//retrieves the list of items in order and converts into stream for processing
                //converts each item in stream into a double
                .mapToDouble(item -> item.getPrice() * item.getQuantity()) //computes total cost of each item
                .sum(); // add all items costs


        // Create the Order entity
        Order order = new Order(orderRequest.getUserId(), orderRequest.getRestaurantId(), totalPrice);
        order.setStatus(OrderStatus.PENDING); //every new order has the pending state
        order = orderRepository.save(order); // Save new order to db


        // Loop through each item in the order and save in db as orderItem entries
        for (OrderItemDTO itemDTO : orderRequest.getOrderItems()) {  // retrieves list of items
            //find menu item in db
            Menu menuItem = menuRepository.findById(itemDTO.getMenuItemId())
                    .orElseThrow(() -> new RuntimeException("Menu item not found"));

            //new OrderItem object
            OrderItem orderItem = new OrderItem(order, menuItem, itemDTO.getQuantity(), itemDTO.getPrice());
            orderItemRepository.save(orderItem); //save each orderItem in db
        }

        return order; //return complete order
    }


    //retrieves an order from db
    public Optional<Order> getOrderById(Long orderId) {

        return orderRepository.findById(orderId); //the order may or may not exist
    }


    // Get Order Status (Only the user who placed it can check it)
    public OrderStatus getOrderStatus(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId) //search order by orderId
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        // Ensure the correct user is requesting status
        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException("You can only track your own orders.");
        }

        return order.getStatus(); //return order status
    }



     // Cancel an Order (Only if it's still pending)
    public String cancelOrder(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId) //find order by order Id
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        // Ensure the correct user is cancelling the order
        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException("You can only cancel your own orders.");
        }

        //ensure only pending orders can be cancelled
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Only pending orders can be cancelled.");
        }

        //change order status to cancel
        order.setStatus(OrderStatus.CANCELLED);
        //save updated order in db
        orderRepository.save(order);
        return "Order has been successfully cancelled.";
    }


    //  Get All Orders for a Specific User
    public List<Order> getOrdersByUserId(Long userId) {
        //call orderRepository method to find user
        return orderRepository.findByUserId(userId);
    }


     // Get All Orders for a Specific Restaurant
    public List<Order> getOrdersByRestaurantId(Long restaurantId) {

        return orderRepository.findByRestaurantId(restaurantId);
    }


    // Update Order Status (Handled by Restaurant Owner)
    public Order updateOrderStatus(Long orderId, OrderStatus status, Long restaurantId) {
        //fetch order from db
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        // Ensure the order belongs to the correct restaurant
        if (!order.getRestaurantId().equals(restaurantId)) {
            throw new RuntimeException("You can only manage orders for your restaurant.");
        }

        // cannot update the oder status if it is delivered or cancelled
        if (order.getStatus() == OrderStatus.DELIVERED || order.getStatus() == OrderStatus.CANCELLED) {
            throw new RuntimeException("Cannot update a completed or cancelled order.");
        }

        // Allow cancellation only if the order is still pending
        if (status == OrderStatus.CANCELLED && order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Orders can only be cancelled while still pending.");
        }

        order.setStatus(status); //update order status
        return orderRepository.save(order); //updates orders status in db
    }

}
