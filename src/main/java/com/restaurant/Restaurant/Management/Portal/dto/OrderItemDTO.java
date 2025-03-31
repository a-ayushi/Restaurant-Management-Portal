package com.restaurant.Restaurant.Management.Portal.dto;

//transfer details of each item in an order
public class OrderItemDTO {

    private Long menuItemId;
    private int quantity;
    private double price;


    //  Default Constructor
    public OrderItemDTO() {}

    //  Parameterized Constructor
    public OrderItemDTO(Long menuItemId, int quantity, double price) {
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.price = price;
    }

    // getters and setters to access and modify data safely
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
