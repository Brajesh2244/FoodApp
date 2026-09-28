package com.fooddelivery.service;

import com.fooddelivery.dto.FoodItemRequest;
import com.fooddelivery.entity.Category;
import com.fooddelivery.entity.FoodItem;
import com.fooddelivery.entity.Restaurant;
import com.fooddelivery.repository.CategoryRepository;
import com.fooddelivery.repository.FoodItemRepository;
import com.fooddelivery.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodItemService {

    private final FoodItemRepository foodItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final CategoryRepository categoryRepository;

    public FoodItemService(
            FoodItemRepository foodItemRepository,
            RestaurantRepository restaurantRepository,
            CategoryRepository categoryRepository) {

        this.foodItemRepository = foodItemRepository;
        this.restaurantRepository = restaurantRepository;
        this.categoryRepository = categoryRepository;
    }

    public FoodItem createFoodItem(FoodItemRequest request) {

        Restaurant restaurant = restaurantRepository
                .findById(request.getRestaurantId())
                .orElseThrow(() ->
                        new RuntimeException("Restaurant not found"));

        Category category = categoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() ->
                        new RuntimeException("Category not found"));

        FoodItem foodItem = new FoodItem();

        foodItem.setName(request.getName());
        foodItem.setDescription(request.getDescription());
        foodItem.setPrice(request.getPrice());
        foodItem.setImageUrl(request.getImageUrl());
        foodItem.setAvailable(request.isAvailable());
        foodItem.setVegetarian(request.isVegetarian());
        foodItem.setRestaurant(restaurant);
        foodItem.setCategory(category);

        return foodItemRepository.save(foodItem);
    }

    public List<FoodItem> getAllFoodItems() {
        return foodItemRepository.findAll();
    }

    public List<FoodItem> getAvailableFoodItems() {
        return foodItemRepository.findByAvailableTrue();
    }

    public FoodItem getFoodItemById(Long id) {

        return foodItemRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Food item not found with id: " + id));
    }

    public FoodItem updateFoodItem(Long id, FoodItemRequest request) {

        FoodItem foodItem = getFoodItemById(id);

        Restaurant restaurant = restaurantRepository
                .findById(request.getRestaurantId())
                .orElseThrow(() ->
                        new RuntimeException("Restaurant not found"));

        Category category = categoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() ->
                        new RuntimeException("Category not found"));

        foodItem.setName(request.getName());
        foodItem.setDescription(request.getDescription());
        foodItem.setPrice(request.getPrice());
        foodItem.setImageUrl(request.getImageUrl());
        foodItem.setAvailable(request.isAvailable());
        foodItem.setVegetarian(request.isVegetarian());
        foodItem.setRestaurant(restaurant);
        foodItem.setCategory(category);

        return foodItemRepository.save(foodItem);
    }

    public void deleteFoodItem(Long id) {

        FoodItem foodItem = getFoodItemById(id);

        foodItemRepository.delete(foodItem);
    }

    public List<FoodItem> getFoodsByRestaurant(Long restaurantId) {
        return foodItemRepository.findByRestaurantId(restaurantId);
    }

    public List<FoodItem> getAvailableFoodsByRestaurant(Long restaurantId) {
        return foodItemRepository
                .findByRestaurantIdAndAvailableTrue(restaurantId);
    }

    public List<FoodItem> getFoodsByCategory(Long categoryId) {
        return foodItemRepository.findByCategoryId(categoryId);
    }

    public List<FoodItem> searchFood(String name) {
        return foodItemRepository.findByNameContainingIgnoreCase(name);
    }

    public List<FoodItem> getVegetarianFoods() {
        return foodItemRepository.findByVegetarianTrue();
    }
}