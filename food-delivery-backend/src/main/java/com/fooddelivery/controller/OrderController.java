package com.fooddelivery.controller;

import com.fooddelivery.dto.PlaceOrderRequest;
import com.fooddelivery.dto.UpdateOrderStatusRequest;
import com.fooddelivery.entity.FoodOrder;
import com.fooddelivery.entity.OrderItem;
import com.fooddelivery.entity.User;
import com.fooddelivery.service.OrderService;
import com.fooddelivery.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;

    public OrderController(OrderService orderService, UserRepository userRepository) {
        this.orderService = orderService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<FoodOrder> placeOrder(
            @RequestBody PlaceOrderRequest request) {

        return new ResponseEntity<>(
                orderService.placeOrder(request),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<FoodOrder>> getAllOrders() {

        return ResponseEntity.ok(
                orderService.getAllOrders()
        );
    }

    @GetMapping("/my")
    public ResponseEntity<List<FoodOrder>> getMyOrders() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(
                orderService.getOrdersByUser(user.getId())
        );
    }

    @GetMapping("/restaurant")
    public ResponseEntity<List<FoodOrder>> getRestaurantOrders() {
        return ResponseEntity.ok(
                orderService.getAllOrders()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodOrder> getOrderById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                orderService.getOrderById(id)
        );
    }

    @GetMapping("/{orderId}/items")
    public ResponseEntity<List<OrderItem>> getOrderItems(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(
                orderService.getOrderItems(orderId)
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FoodOrder>> getOrdersByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                orderService.getOrdersByUser(userId)
        );
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<FoodOrder> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody UpdateOrderStatusRequest request) {

        return ResponseEntity.ok(
                orderService.updateOrderStatus(
                        orderId,
                        request.getStatus()
                )
        );
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<FoodOrder> cancelOrder(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(
                orderService.cancelOrder(orderId)
        );
    }
}