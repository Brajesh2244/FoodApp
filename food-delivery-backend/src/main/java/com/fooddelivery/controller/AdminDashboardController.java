package com.fooddelivery.controller;

import com.fooddelivery.dto.AdminDashboardResponse;
import com.fooddelivery.entity.FoodOrder;
import com.fooddelivery.entity.Restaurant;
import com.fooddelivery.entity.User;
import com.fooddelivery.repository.FoodOrderRepository;
import com.fooddelivery.repository.RestaurantRepository;
import com.fooddelivery.repository.UserRepository;
import com.fooddelivery.service.AdminDashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodOrderRepository foodOrderRepository;

    public AdminDashboardController(
            AdminDashboardService adminDashboardService,
            UserRepository userRepository,
            RestaurantRepository restaurantRepository,
            FoodOrderRepository foodOrderRepository) {

        this.adminDashboardService = adminDashboardService;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodOrderRepository = foodOrderRepository;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<AdminDashboardResponse> getDashboardStatistics() {
        return ResponseEntity.ok(adminDashboardService.getDashboardStatistics());
    }

    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<User> users = userRepository.findAll();
        Map<String, Object> response = new HashMap<>();
        response.put("content", users);
        response.put("totalElements", users.size());
        response.put("totalPages", (int) Math.ceil((double) users.size() / size));
        response.put("number", page);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/users/{id}/status")
    public ResponseEntity<User> updateUserStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (body.containsKey("role")) {
            user.setRole(body.get("role"));
        }
        return ResponseEntity.ok(userRepository.save(user));
    }

    @GetMapping("/restaurants")
    public ResponseEntity<Map<String, Object>> getRestaurants(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        Map<String, Object> response = new HashMap<>();
        response.put("content", restaurants);
        response.put("totalElements", restaurants.size());
        response.put("totalPages", (int) Math.ceil((double) restaurants.size() / size));
        response.put("number", page);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/restaurants/{id}/approve")
    public ResponseEntity<Restaurant> approveRestaurant(@PathVariable Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        restaurant.setActive(true);
        return ResponseEntity.ok(restaurantRepository.save(restaurant));
    }

    @PutMapping("/restaurants/{id}/disable")
    public ResponseEntity<Restaurant> disableRestaurant(@PathVariable Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        restaurant.setActive(false);
        return ResponseEntity.ok(restaurantRepository.save(restaurant));
    }

    @GetMapping("/orders")
    public ResponseEntity<Map<String, Object>> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<FoodOrder> orders = foodOrderRepository.findAll();
        Map<String, Object> response = new HashMap<>();
        response.put("content", orders);
        response.put("totalElements", orders.size());
        response.put("totalPages", (int) Math.ceil((double) orders.size() / size));
        response.put("number", page);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/analytics/orders")
    public ResponseEntity<Map<String, Object>> getOrderAnalytics() {
        Map<String, Object> analytics = new HashMap<>();
        Map<String, Long> ordersByStatus = new HashMap<>();
        for (com.fooddelivery.entity.OrderStatus st : com.fooddelivery.entity.OrderStatus.values()) {
            long count = foodOrderRepository.countByStatus(st);
            if (count > 0) {
                ordersByStatus.put(st.name(), count);
            }
        }
        if (ordersByStatus.isEmpty()) {
            ordersByStatus.put("PENDING", 0L);
        }
        analytics.put("totalOrders", foodOrderRepository.count());
        analytics.put("ordersByStatus", ordersByStatus);
        return ResponseEntity.ok(analytics);
    }

    @GetMapping("/analytics/revenue")
    public ResponseEntity<List<Map<String, Object>>> getRevenueAnalytics(
            @RequestParam(defaultValue = "30") int days) {
        List<Map<String, Object>> list = new java.util.ArrayList<>();
        java.time.LocalDate today = java.time.LocalDate.now();
        List<FoodOrder> allOrders = foodOrderRepository.findAll();

        for (int i = days - 1; i >= 0; i--) {
            java.time.LocalDate date = today.minusDays(i);
            BigDecimal dayRevenue = allOrders.stream()
                    .filter(o -> o.getOrderDate() != null && o.getOrderDate().toLocalDate().equals(date))
                    .map(FoodOrder::getTotalAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            Map<String, Object> entry = new HashMap<>();
            entry.put("date", date.toString());
            entry.put("revenue", dayRevenue);
            list.add(entry);
        }
        return ResponseEntity.ok(list);
    }
}