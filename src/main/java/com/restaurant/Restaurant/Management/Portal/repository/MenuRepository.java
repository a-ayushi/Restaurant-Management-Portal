package com.restaurant.Restaurant.Management.Portal.repository;

import com.restaurant.Restaurant.Management.Portal.model.Menu;
import com.restaurant.Restaurant.Management.Portal.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    //custom query method to fetch all menu items
    List<Menu> findByRestaurant(Restaurant restaurant);
}
