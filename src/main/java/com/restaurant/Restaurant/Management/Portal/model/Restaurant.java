package com.restaurant.Restaurant.Management.Portal.model;

import jakarta.persistence.*;

@Entity
@Table(name="restuarants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable = false)
    private String address;

    @ManyToOne // Each restaurant has one owner
    @JoinColumn(name="owner_id", nullable = false)
    private User owner;//the restaurant owner

    //Default constructor
    public Restaurant(){}

    //Parameterized Constructor
    public Restaurant(String name,String address,User owner) {
        this.name = name;
        this.address=address;
        this.owner=owner;
    }

    //Getters and Setters

    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName() {
        this.name = name;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getOwner(){
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
