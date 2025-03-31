package com.restaurant.Restaurant.Management.Portal.model;
import jakarta.persistence.*;


// menu table contains the details of menu items
// added by the restaurant owners

@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;

    // Links menu item to a restaurant
    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    // Stores menu item image URL
    private String imageUrl;


    // default constructor
    public Menu() {}

    //parameterized constructor
    public Menu(String name, double price, Restaurant restaurant,String imageUrl) {
        this.name = name;
        this.price = price;
        this.restaurant = restaurant;
        this.imageUrl=imageUrl;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public Restaurant getRestaurant() { return restaurant; }
    public void setRestaurant(Restaurant restaurant) { this.restaurant = restaurant; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}

