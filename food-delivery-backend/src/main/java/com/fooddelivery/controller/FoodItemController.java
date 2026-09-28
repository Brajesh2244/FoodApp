package com.fooddelivery.controller;

import com.fooddelivery.dto.FoodItemRequest;
import com.fooddelivery.entity.FoodItem;
import com.fooddelivery.service.FoodItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
@CrossOrigin(origins = "*")
public class FoodItemController {

    private final FoodItemService foodItemService;

    public FoodItemController(FoodItemService foodItemService) {
        this.foodItemService = foodItemService;
    }

    @PostMapping
    public ResponseEntity<FoodItem> createFoodItem(
            @RequestBody FoodItemRequest request) {

        return new ResponseEntity<>(
                foodItemService.createFoodItem(request),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<FoodItem>> getAllFoodItems() {
        return ResponseEntity.ok(
                foodItemService.getAllFoodItems()
        );
    }

    @GetMapping("/available")
    public ResponseEntity<List<FoodItem>> getAvailableFoodItems() {
        return ResponseEntity.ok(
                foodItemService.getAvailableFoodItems()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodItem> getFoodItemById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                foodItemService.getFoodItemById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodItem> updateFoodItem(
            @PathVariable Long id,
            @RequestBody FoodItemRequest request) {

        return ResponseEntity.ok(
                foodItemService.updateFoodItem(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFoodItem(
            @PathVariable Long id) {

        foodItemService.deleteFoodItem(id);

        return ResponseEntity.ok("Food item deleted successfully");
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<FoodItem>> getFoodsByRestaurant(
            @PathVariable Long restaurantId) {

        return ResponseEntity.ok(
                foodItemService.getFoodsByRestaurant(restaurantId)
        );
    }

    @GetMapping("/restaurant/{restaurantId}/available")
    public ResponseEntity<List<FoodItem>> getAvailableFoodsByRestaurant(
            @PathVariable Long restaurantId) {

        return ResponseEntity.ok(
                foodItemService.getAvailableFoodsByRestaurant(restaurantId)
        );
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<FoodItem>> getFoodsByCategory(
            @PathVariable Long categoryId) {

        return ResponseEntity.ok(
                foodItemService.getFoodsByCategory(categoryId)
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<FoodItem>> searchFood(
            @RequestParam String name) {

        return ResponseEntity.ok(
                foodItemService.searchFood(name)
        );
    }

    @GetMapping("/vegetarian")
    public ResponseEntity<List<FoodItem>> getVegetarianFoods() {

        return ResponseEntity.ok(
                foodItemService.getVegetarianFoods()
        );
    }
}