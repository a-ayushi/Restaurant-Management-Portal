// it will receive order details from frontend


package com.restaurant.Restaurant.Management.Portal.dto;
import java.util.List;

public class OrderRequestDTO {
    private Long userId;
    private Long restaurantId;
    private List<OrderItemDTO> orderItems;


    // Default Constructor (Good practice for serialization)
    public OrderRequestDTO() {}

    //  Parameterized Constructor
    public OrderRequestDTO(Long userId, Long restaurantId, List<OrderItemDTO> orderItems) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.orderItems = orderItems;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }
}
