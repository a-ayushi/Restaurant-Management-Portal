package com.restaurant.Restaurant.Management.Portal.model;

import jakarta.persistence.*;



//the User class defines the user table
// it has four attributes, it stores the details of registered user

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable=false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)  // Converts enum to string in DB
    @Column(nullable = false)
    private Role role; // customer or restaurant owner

//default constructor
    public User(){}


// parameterized constructor
public User(String email, String password, Role role) {
    this.email = email;
    this.password = password;
    this.role = role;
}

//Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id=id;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password=password;
    }

    public Role getRole(){
        return role;
    }
    public void setRole(Role role){
        this.role=role;
    }

}

