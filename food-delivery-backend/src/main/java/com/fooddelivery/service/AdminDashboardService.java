package com.fooddelivery.service;

import com.fooddelivery.dto.AdminDashboardResponse;
import com.fooddelivery.entity.DeliveryPartnerStatus;
import com.fooddelivery.entity.FoodOrder;
import com.fooddelivery.entity.OrderStatus;
import com.fooddelivery.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AdminDashboardService {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final CategoryRepository categoryRepository;
    private final FoodItemRepository foodItemRepository;
    private final FoodOrderRepository orderRepository;
    private final DeliveryPartnerRepository deliveryPartnerRepository;

    public AdminDashboardService(
            UserRepository userRepository,
            RestaurantRepository restaurantRepository,
            CategoryRepository categoryRepository,
            FoodItemRepository foodItemRepository,
            FoodOrderRepository orderRepository,
            DeliveryPartnerRepository deliveryPartnerRepository) {

        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.categoryRepository = categoryRepository;
        this.foodItemRepository = foodItemRepository;
        this.orderRepository = orderRepository;
        this.deliveryPartnerRepository = deliveryPartnerRepository;
    }

    public AdminDashboardResponse getDashboardStatistics() {

        AdminDashboardResponse response =
                new AdminDashboardResponse();

        // Basic counts
        response.setTotalUsers(userRepository.count());

        response.setTotalRestaurants(
                restaurantRepository.count()
        );

        response.setTotalCategories(
                categoryRepository.count()
        );

        response.setTotalFoodItems(
                foodItemRepository.count()
        );

        // Order counts
        response.setTotalOrders(
                orderRepository.count()
        );

        response.setPendingOrders(
                orderRepository.countByStatus(
                        OrderStatus.PENDING
                )
        );

        response.setConfirmedOrders(
                orderRepository.countByStatus(
                        OrderStatus.CONFIRMED
                )
        );

        response.setPreparingOrders(
                orderRepository.countByStatus(
                        OrderStatus.PREPARING
                )
        );

        response.setOutForDeliveryOrders(
                orderRepository.countByStatus(
                        OrderStatus.OUT_FOR_DELIVERY
                )
        );

        response.setDeliveredOrders(
                orderRepository.countByStatus(
                        OrderStatus.DELIVERED
                )
        );

        response.setCancelledOrders(
                orderRepository.countByStatus(
                        OrderStatus.CANCELLED
                )
        );

        // Delivery partner counts
        response.setTotalDeliveryPartners(
                deliveryPartnerRepository.count()
        );

        response.setAvailableDeliveryPartners(
                deliveryPartnerRepository
                        .findByStatus(
                                DeliveryPartnerStatus.AVAILABLE
                        )
                        .size()
        );

        response.setBusyDeliveryPartners(
                deliveryPartnerRepository
                        .findByStatus(
                                DeliveryPartnerStatus.BUSY
                        )
                        .size()
        );

        // Revenue calculation
        List<FoodOrder> deliveredOrders =
                orderRepository.findByStatus(
                        OrderStatus.DELIVERED
                );

        BigDecimal totalRevenue = deliveredOrders
                .stream()
                .map(FoodOrder::getTotalAmount)
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );

        response.setTotalRevenue(totalRevenue);

        return response;
    }
}