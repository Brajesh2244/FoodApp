package com.fooddelivery.repository;

import com.fooddelivery.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {

    List<FoodItem> findByAvailableTrue();

    @Query("SELECT f FROM FoodItem f WHERE f.restaurant.id = :restaurantId")
    List<FoodItem> findByRestaurantId(@Param("restaurantId") Long restaurantId);

    @Query("SELECT f FROM FoodItem f WHERE f.category.id = :categoryId")
    List<FoodItem> findByCategoryId(@Param("categoryId") Long categoryId);

    List<FoodItem> findByNameContainingIgnoreCase(String name);

    List<FoodItem> findByVegetarianTrue();

    @Query("SELECT f FROM FoodItem f WHERE f.restaurant.id = :restaurantId AND f.available = true")
    List<FoodItem> findByRestaurantIdAndAvailableTrue(@Param("restaurantId") Long restaurantId);
}