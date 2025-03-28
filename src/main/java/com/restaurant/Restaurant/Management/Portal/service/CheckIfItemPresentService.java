package com.restaurant.Restaurant.Management.Portal.service;


import com.restaurant.Restaurant.Management.Portal.repository.CheckIfItemPresentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckIfItemPresentService {
    @Autowired
    private CheckIfItemPresentRepository checkIfItemPresentRepository;

    public boolean isItemInCart(Long userId,Long menuItemId){
        return checkIfItemPresentRepository.existsByUserIdAndMenuItemId(userId,menuItemId);
    }
}
