package com.restaurant.Restaurant.Management.Portal.model;
import jakarta.persistence.*;
@Entity
@Table(name="menu_items")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private  double price;

    @ManyToOne
    @JoinColumn(name="restaurant_id",nullable = false)
    private Restaurant restaurant;//link menu item to a restaurant


    //default constructor
    public MenuItem(){}

    //parameterized Constructor
    public MenuItem(String name,double price, Restaurant restaurant){
        this.name=name;
        this.price=price;
        this.restaurant=restaurant;
    }

    //getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
