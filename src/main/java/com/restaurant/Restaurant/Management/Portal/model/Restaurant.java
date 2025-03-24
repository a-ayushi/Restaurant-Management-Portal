package com.restaurant.Restaurant.Management.Portal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    // This field stores the ID of the user associated with this restaurant.
    private Long userId;

    // Default constructor
    public Restaurant() {}

    // Parameterized constructor (without userId)
    public Restaurant(String name, String address) {
        this.name = name;
        this.address = address;
    }
    
    // Parameterized constructor (with userId)
    public Restaurant(String name, String address, Long userId) {
        this.name = name;
        this.address = address;
        this.userId = userId;
    }

    // Getters and Setters
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // Optional: Override toString() for better logging/debugging
    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", userId=" + userId +
                '}';
    }
}
