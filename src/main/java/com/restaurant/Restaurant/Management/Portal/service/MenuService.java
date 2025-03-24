package com.restaurant.Restaurant.Management.Portal.service;

import com.restaurant.Restaurant.Management.Portal.model.Menu;
import com.restaurant.Restaurant.Management.Portal.model.Restaurant;
import com.restaurant.Restaurant.Management.Portal.repository.MenuRepository;
import com.restaurant.Restaurant.Management.Portal.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    // Fetch menu by restaurant ID
    public List<Menu> getMenuByRestaurant(Long restaurantId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        return restaurant.map(menuRepository::findByRestaurant).orElseThrow(() -> new RuntimeException("Restaurant not found"));
    }

    // Add a new menu item
    public Menu addMenuItem(Long restaurantId, Menu menu) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        menu.setRestaurant(restaurant);
        return menuRepository.save(menu);
    }

    //update menu item
    public Menu updateMenuItem(Long menuId, Menu updatedMenu) {
        Menu existingMenu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        existingMenu.setName(updatedMenu.getName());
        existingMenu.setPrice(updatedMenu.getPrice());

        return menuRepository.save(existingMenu);
    }

    //delete menu item
    public void deleteMenuItem(Long menuId) {
        if (!menuRepository.existsById(menuId)) {
            throw new RuntimeException("Menu item not found");
        }
        menuRepository.deleteById(menuId);
    }

}
