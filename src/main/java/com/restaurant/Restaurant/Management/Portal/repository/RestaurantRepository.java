package com.restaurant.Restaurant.Management.Portal.repository;

import com.restaurant.Restaurant.Management.Portal.model.Restaurant;
import com.restaurant.Restaurant.Management.Portal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
  List<Restaurant> findByOwner(User owner);
}

