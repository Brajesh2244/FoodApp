package com.fooddelivery.repository;

import com.fooddelivery.entity.FoodOrder;
import com.fooddelivery.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodOrderRepository extends JpaRepository<FoodOrder, Long> {

    // Count orders by status
    long countByStatus(OrderStatus status);

    // Find orders by status
    List<FoodOrder> findByStatus(OrderStatus status);

    // Find all orders of a user, latest orders first
    List<FoodOrder> findByUserIdOrderByOrderDateDesc(Long userId);

}