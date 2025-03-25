package com.restaurant.Restaurant.Management.Portal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ✅ Unique ID for cart item

    @ManyToOne
    @JoinColumn(name = "menu_item_id", nullable = false)
    private Menu menuItem; // ✅ Menu item being added to cart

    private String itemName; // ✅ Store the name of the menu item
    private double itemPrice; // ✅ Store the price of the menu item
    private String imageUrl; //  Store the image URL of the menu item
    private int quantity; // Quantity of item in cart


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // ✅ User who added the item to the cart

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant; // ✅ Restaurant from which the item is added

    // ✅ Default Constructor
    public Cart() {}

    // ✅ Parameterized Constructor
    public Cart(Menu menuItem, String itemName, double itemPrice, int quantity, User user, Restaurant restaurant,String imageUrl) {
        this.menuItem = menuItem;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
        this.user = user;
        this.restaurant = restaurant;
        this.imageUrl=imageUrl;
    }

    // ✅ Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Menu getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(Menu menuItem) {
        this.menuItem = menuItem;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getImageUrl() { //  Getter for Image URL
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) { //  Setter for Image URL
        this.imageUrl = imageUrl;
    }

}
