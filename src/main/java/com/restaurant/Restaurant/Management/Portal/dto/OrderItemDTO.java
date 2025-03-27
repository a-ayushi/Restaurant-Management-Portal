package com.restaurant.Restaurant.Management.Portal.dto;

public class OrderItemDTO {

    private Long menuItemId;
    private int quantity;
    private double price;


    //  Default Constructor (Required for serialization)
    public OrderItemDTO() {}

    //  Parameterized Constructor (Optional but useful)
    public OrderItemDTO(Long menuItemId, int quantity, double price) {
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
