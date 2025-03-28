// it prevents duplicate entries in the cart
// add items only if they are not already present in the cart

package com.restaurant.Restaurant.Management.Portal.controller;


import com.restaurant.Restaurant.Management.Portal.service.CheckIfItemPresentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/check")
@CrossOrigin(origins = "http://localhost:63342", allowCredentials = "true")
public class CheckIfItemPresentController {

    @Autowired
    private CheckIfItemPresentService checkIfItemPresentService;

    @GetMapping("/ifpresent")
    public ResponseEntity<Boolean> checkItemInCart(@RequestParam Long userId, @RequestParam Long menuItemId) {
        boolean exists = checkIfItemPresentService.isItemInCart(userId, menuItemId);
        return ResponseEntity.ok(exists);
    }
}
