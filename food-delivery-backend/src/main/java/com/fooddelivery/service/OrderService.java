package com.fooddelivery.service;

import com.fooddelivery.dto.PlaceOrderRequest;
import com.fooddelivery.entity.*;
import com.fooddelivery.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final FoodOrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PaymentRepository paymentRepository;

    public OrderService(
            FoodOrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            UserRepository userRepository,
            AddressRepository addressRepository,
            PaymentRepository paymentRepository) {

        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public FoodOrder placeOrder(PlaceOrderRequest request) {

        User user;
        if (request.getUserId() != null) {
            user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
        } else {
            String email = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
            user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
        }

        Address address = addressRepository.findById(request.getAddressId())
                .orElseThrow(() ->
                        new RuntimeException("Address not found"));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new RuntimeException("Cart not found"));

        List<CartItem> cartItems =
                cartItemRepository.findByCartId(cart.getId());

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        BigDecimal subtotal = cart.getTotalAmount();

        BigDecimal deliveryFee = new BigDecimal("40.00");

        BigDecimal totalAmount =
                subtotal.add(deliveryFee);

        FoodOrder order = new FoodOrder();

        order.setOrderNumber(
                "ORD-" + UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase()
        );

        order.setUser(user);
        order.setDeliveryAddress(address);
        order.setStatus(OrderStatus.PENDING);
        order.setSubtotal(subtotal);
        order.setDeliveryFee(deliveryFee);
        order.setTotalAmount(totalAmount);
        order.setPaymentMethod(request.getPaymentMethod());
        order.setPaymentStatus("PENDING");

        FoodOrder savedOrder =
                orderRepository.save(order);

        // Auto-create Payment entity for order
        Payment payment = new Payment();
        payment.setOrder(savedOrder);
        payment.setTransactionId("TXN-" + UUID.randomUUID().toString().substring(0, 10).toUpperCase());
        payment.setPaymentMethod(request.getPaymentMethod() != null ? request.getPaymentMethod() : "CASH_ON_DELIVERY");
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setAmount(totalAmount);
        paymentRepository.save(payment);

        for (CartItem cartItem : cartItems) {

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(savedOrder);
            orderItem.setFoodItem(cartItem.getFoodItem());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());

            BigDecimal itemTotal =
                    cartItem.getPrice().multiply(
                            BigDecimal.valueOf(
                                    cartItem.getQuantity()
                            )
                    );

            orderItem.setTotalPrice(itemTotal);

            orderItemRepository.save(orderItem);
        }

        // Clear cart after successful order
        cartItemRepository.deleteAll(cartItems);

        cart.setTotalAmount(BigDecimal.ZERO);
        cartRepository.save(cart);

        return savedOrder;
    }

    public FoodOrder getOrderById(Long id) {

        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));
    }

    public List<FoodOrder> getOrdersByUser(Long userId) {

        return orderRepository
                .findByUserIdOrderByOrderDateDesc(userId);
    }

    public List<FoodOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<OrderItem> getOrderItems(Long orderId) {

        getOrderById(orderId);

        return orderItemRepository.findByOrderId(orderId);
    }

    public FoodOrder updateOrderStatus(
            Long orderId,
            OrderStatus status) {

        FoodOrder order = getOrderById(orderId);

        order.setStatus(status);

        return orderRepository.save(order);
    }

    public FoodOrder cancelOrder(Long orderId) {

        FoodOrder order = getOrderById(orderId);

        if (order.getStatus() == OrderStatus.DELIVERED) {
            throw new RuntimeException(
                    "Delivered order cannot be cancelled"
            );
        }

        order.setStatus(OrderStatus.CANCELLED);

        return orderRepository.save(order);
    }
}