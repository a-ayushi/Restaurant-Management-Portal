package com.restaurant.Restaurant.Management.Portal.repository;

import com.restaurant.Restaurant.Management.Portal.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long>{ // enable basic CRUD operaions
  List<Restaurant> findByOwner(Long OwnerId); // fetch the restaurant by a specific owner
}
