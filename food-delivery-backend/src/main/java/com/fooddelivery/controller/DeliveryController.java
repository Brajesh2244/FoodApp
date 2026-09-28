package com.fooddelivery.controller;

import com.fooddelivery.dto.AssignDeliveryRequest;
import com.fooddelivery.dto.UpdateDeliveryStatusRequest;
import com.fooddelivery.entity.Delivery;
import com.fooddelivery.service.DeliveryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
@CrossOrigin(origins = "*")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(
            DeliveryService deliveryService) {

        this.deliveryService = deliveryService;
    }

    @PostMapping("/assign")
    public ResponseEntity<Delivery> assignDelivery(
            @RequestBody AssignDeliveryRequest request) {

        return new ResponseEntity<>(
                deliveryService.assignDelivery(request),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<Delivery>>
    getAllDeliveries() {

        return ResponseEntity.ok(
                deliveryService.getAllDeliveries()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Delivery>
    getDeliveryById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                deliveryService.getDeliveryById(id)
        );
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Delivery>
    getDeliveryByOrderId(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(
                deliveryService
                        .getDeliveryByOrderId(orderId)
        );
    }

    @GetMapping("/partner/{partnerId}")
    public ResponseEntity<List<Delivery>>
    getDeliveriesByPartner(
            @PathVariable Long partnerId) {

        return ResponseEntity.ok(
                deliveryService
                        .getDeliveriesByPartner(partnerId)
        );
    }

    @PutMapping("/{deliveryId}/status")
    public ResponseEntity<Delivery>
    updateDeliveryStatus(
            @PathVariable Long deliveryId,
            @RequestBody
            UpdateDeliveryStatusRequest request) {

        return ResponseEntity.ok(
                deliveryService.updateDeliveryStatus(
                        deliveryId,
                        request.getStatus()
                )
        );
    }
}