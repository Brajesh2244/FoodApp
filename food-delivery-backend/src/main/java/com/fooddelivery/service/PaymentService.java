package com.fooddelivery.service;

import com.fooddelivery.dto.CreatePaymentRequest;
import com.fooddelivery.entity.FoodOrder;
import com.fooddelivery.entity.Payment;
import com.fooddelivery.entity.PaymentStatus;
import com.fooddelivery.repository.FoodOrderRepository;
import com.fooddelivery.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final FoodOrderRepository orderRepository;

    public PaymentService(
            PaymentRepository paymentRepository,
            FoodOrderRepository orderRepository) {

        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    public Payment createPayment(CreatePaymentRequest request) {

        FoodOrder order = orderRepository
                .findById(request.getOrderId())
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        Optional<Payment> existingOpt = paymentRepository.findByOrderId(order.getId());
        if (existingOpt.isPresent()) {
            return existingOpt.get();
        }

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setTransactionId(
                "TXN-" + UUID.randomUUID()
                        .toString()
                        .substring(0, 12)
                        .toUpperCase()
        );
        payment.setPaymentMethod(request.getPaymentMethod() != null ? request.getPaymentMethod() : "CASH_ON_DELIVERY");
        payment.setAmount(order.getTotalAmount());
        payment.setPaymentStatus(PaymentStatus.PENDING);

        return paymentRepository.save(payment);
    }

    public java.util.Map<String, Object> createCheckoutSession(Long orderId) {
        FoodOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseGet(() -> {
                    Payment p = new Payment();
                    p.setOrder(order);
                    p.setTransactionId("SESSION_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16).toUpperCase());
                    p.setPaymentMethod("ONLINE_PAYMENT");
                    p.setAmount(order.getTotalAmount());
                    return p;
                });

        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        payment.setPaymentDate(LocalDateTime.now());
        Payment savedPayment = paymentRepository.save(payment);

        order.setPaymentStatus("SUCCESS");
        order.setStatus(com.fooddelivery.entity.OrderStatus.CONFIRMED);
        orderRepository.save(order);

        java.util.Map<String, Object> response = new java.util.HashMap<>();
        response.put("sessionId", savedPayment.getTransactionId());
        response.put("sessionUrl", "http://localhost:5175/payment/success?session_id=" + savedPayment.getTransactionId());
        return response;
    }

    public java.util.Map<String, Object> getPaymentBySessionId(String sessionId) {
        Payment payment = paymentRepository.findByTransactionId(sessionId)
                .orElseThrow(() -> new RuntimeException("Payment transaction not found for session: " + sessionId));

        java.util.Map<String, Object> map = new java.util.HashMap<>();
        map.put("id", payment.getId());
        map.put("orderId", payment.getOrder().getId());
        map.put("orderNumber", payment.getOrder().getOrderNumber());
        map.put("transactionId", payment.getTransactionId());
        map.put("paymentMethod", payment.getPaymentMethod());
        map.put("paymentStatus", payment.getPaymentStatus().name());
        map.put("amount", payment.getAmount());
        map.put("paymentDate", payment.getPaymentDate());
        return map;
    }

    public Payment getPaymentById(Long id) {

        return paymentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Payment not found"));
    }

    public Payment getPaymentByOrderId(Long orderId) {

        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Payment not found for this order"
                        ));
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public List<Payment> getPaymentsByStatus(
            PaymentStatus paymentStatus) {

        return paymentRepository
                .findByPaymentStatus(paymentStatus);
    }

    public Payment updatePaymentStatus(
            Long paymentId,
            PaymentStatus paymentStatus) {

        Payment payment = getPaymentById(paymentId);

        payment.setPaymentStatus(paymentStatus);

        if (paymentStatus == PaymentStatus.SUCCESS) {

            payment.setPaymentDate(LocalDateTime.now());

            FoodOrder order = payment.getOrder();

            order.setPaymentStatus("SUCCESS");

            orderRepository.save(order);

        } else if (paymentStatus == PaymentStatus.FAILED) {

            FoodOrder order = payment.getOrder();

            order.setPaymentStatus("FAILED");

            orderRepository.save(order);

        }

        return paymentRepository.save(payment);
    }
}