package com.fooddelivery.controller;

import com.fooddelivery.dto.CreatePaymentRequest;
import com.fooddelivery.dto.UpdatePaymentStatusRequest;
import com.fooddelivery.entity.Payment;
import com.fooddelivery.entity.PaymentStatus;
import com.fooddelivery.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<Payment> createPayment(
            @RequestBody CreatePaymentRequest request) {

        return new ResponseEntity<>(
                paymentService.createPayment(request),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/create/{orderId}")
    public ResponseEntity<Payment> createPaymentForOrder(
            @PathVariable Long orderId,
            @RequestBody(required = false) CreatePaymentRequest request) {
        CreatePaymentRequest req = request != null ? request : new CreatePaymentRequest();
        req.setOrderId(orderId);
        return new ResponseEntity<>(
                paymentService.createPayment(req),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/checkout/{orderId}")
    public ResponseEntity<java.util.Map<String, Object>> createCheckoutSession(
            @PathVariable Long orderId) {
        return ResponseEntity.ok(paymentService.createCheckoutSession(orderId));
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<java.util.Map<String, Object>> getPaymentBySessionId(
            @PathVariable String sessionId) {
        return ResponseEntity.ok(paymentService.getPaymentBySessionId(sessionId));
    }

    @PostMapping("/verify")
    public ResponseEntity<java.util.Map<String, Object>> verifyPayment(
            @RequestBody java.util.Map<String, Object> body) {
        String sessionId = (String) body.get("sessionId");
        if (sessionId == null) sessionId = (String) body.get("session_id");
        if (sessionId != null) {
            return ResponseEntity.ok(paymentService.getPaymentBySessionId(sessionId));
        }
        return ResponseEntity.ok(java.util.Map.of("status", "SUCCESS"));
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {

        return ResponseEntity.ok(
                paymentService.getAllPayments()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                paymentService.getPaymentById(id)
        );
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<java.util.Map<String, Object>> getPaymentByOrderId(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(
                paymentService.getPaymentBySessionId(
                        paymentService.getPaymentByOrderId(orderId).getTransactionId()
                )
        );
    }

    @GetMapping("/status/{paymentStatus}")
    public ResponseEntity<List<Payment>> getPaymentsByStatus(
            @PathVariable PaymentStatus paymentStatus) {

        return ResponseEntity.ok(
                paymentService.getPaymentsByStatus(paymentStatus)
        );
    }

    @PutMapping("/{paymentId}/status")
    public ResponseEntity<Payment> updatePaymentStatus(
            @PathVariable Long paymentId,
            @RequestBody UpdatePaymentStatusRequest request) {

        return ResponseEntity.ok(
                paymentService.updatePaymentStatus(
                        paymentId,
                        request.getPaymentStatus()
                )
        );
    }
}