package com.fooddelivery.controller;

import com.fooddelivery.dto.RestaurantRequest;
import com.fooddelivery.entity.Restaurant;
import com.fooddelivery.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@CrossOrigin(origins = "*")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    // Create Restaurant
    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(
            @RequestBody RestaurantRequest request) {

        Restaurant restaurant =
                restaurantService.createRestaurant(request);

        return new ResponseEntity<>(
                restaurant,
                HttpStatus.CREATED
        );
    }

    // Get All Restaurants
    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {

        return ResponseEntity.ok(
                restaurantService.getAllRestaurants()
        );
    }

    // Get Restaurant By ID
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                restaurantService.getRestaurantById(id)
        );
    }

    // Update Restaurant
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(
            @PathVariable Long id,
            @RequestBody RestaurantRequest request) {

        return ResponseEntity.ok(
                restaurantService.updateRestaurant(id, request)
        );
    }

    // Delete Restaurant
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRestaurant(
            @PathVariable Long id) {

        restaurantService.deleteRestaurant(id);

        return ResponseEntity.ok(
                "Restaurant deleted successfully"
        );
    }
}