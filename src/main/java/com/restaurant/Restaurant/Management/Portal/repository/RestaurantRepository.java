package com.restaurant.Restaurant.Management.Portal.repository;

import com.restaurant.Restaurant.Management.Portal.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
  // Additional queries if needed
}
