package com.restaurant.Restaurant.Management.Portal.model;

import jakarta.persistence.*;
//import lombok.*;

@Entity
@Table(name="users")
//@Getter
//@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable=false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // customer or restaurant owner
}
