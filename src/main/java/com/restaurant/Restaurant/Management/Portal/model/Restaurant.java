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

    //store image url
    private String imageUrl;

    // Default constructor
    public Restaurant() {}

    // Parameterized constructor (without userId)
    public Restaurant(String name, String address,String imageUrl) {
        this.name = name;
        this.address = address;
        this.imageUrl=imageUrl;
    }
    
    // Parameterized constructor (with userId)
    public Restaurant(String name, String address, Long userId,String imageUrl) {
        this.name = name;
        this.address = address;
        this.userId = userId;
        this.imageUrl=imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // Optional: Override toString() for better logging/debugging
    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", userId=" + userId +'\''+
                ", imageUrl=" +imageUrl+
                '}';
    }
    public void setOwnerId(Long ownerId) {
        this.userId = ownerId;
}

}
