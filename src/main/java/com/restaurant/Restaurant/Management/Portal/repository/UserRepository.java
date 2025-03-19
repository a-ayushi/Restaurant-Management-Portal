package com.restaurant.Restaurant.Management.Portal.repository;


import com.restaurant.Restaurant.Management.Portal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository  extends  JpaRepository<User,Long>{
    Optional<User> findByEmail(String email);
}
