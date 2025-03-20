package com.restaurant.Restaurant.Management.Portal.repository;
import com.restaurant.Restaurant.Management.Portal.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MenuItemRepository  extends JpaRepository<MenuItem,Long>{
    List<MenuItem> findByRestaurantId(Long restaurantId); //fetch menu items for a specific restaurant
}
