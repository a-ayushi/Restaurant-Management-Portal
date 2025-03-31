package com.restaurant.Restaurant.Management.Portal.repository;


import com.restaurant.Restaurant.Management.Portal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


//manages User entities in DB
public interface UserRepository  extends  JpaRepository<User,Long>{
    //it used in login and registration process
    Optional<User> findByEmail(String email);
}
