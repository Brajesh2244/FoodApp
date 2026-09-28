package com.fooddelivery.controller;

import com.fooddelivery.dto.DeliveryPartnerRequest;
import com.fooddelivery.dto.UpdateDeliveryPartnerStatusRequest;
import com.fooddelivery.entity.DeliveryPartner;
import com.fooddelivery.service.DeliveryPartnerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery-partners")
@CrossOrigin(origins = "*")
public class DeliveryPartnerController {

    private final DeliveryPartnerService deliveryPartnerService;

    public DeliveryPartnerController(
            DeliveryPartnerService deliveryPartnerService) {

        this.deliveryPartnerService =
                deliveryPartnerService;
    }

    @PostMapping
    public ResponseEntity<DeliveryPartner>
    createDeliveryPartner(
            @RequestBody DeliveryPartnerRequest request) {

        return new ResponseEntity<>(
                deliveryPartnerService
                        .createDeliveryPartner(request),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<DeliveryPartner>>
    getAllDeliveryPartners() {

        return ResponseEntity.ok(
                deliveryPartnerService
                        .getAllDeliveryPartners()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryPartner>
    getDeliveryPartnerById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                deliveryPartnerService
                        .getDeliveryPartnerById(id)
        );
    }

    @GetMapping("/available")
    public ResponseEntity<List<DeliveryPartner>>
    getAvailableDeliveryPartners() {

        return ResponseEntity.ok(
                deliveryPartnerService
                        .getAvailableDeliveryPartners()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeliveryPartner>
    updateDeliveryPartner(
            @PathVariable Long id,
            @RequestBody DeliveryPartnerRequest request) {

        return ResponseEntity.ok(
                deliveryPartnerService
                        .updateDeliveryPartner(id, request)
        );
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<DeliveryPartner>
    updateStatus(
            @PathVariable Long id,
            @RequestBody
            UpdateDeliveryPartnerStatusRequest request) {

        return ResponseEntity.ok(
                deliveryPartnerService.updateStatus(
                        id,
                        request.getStatus()
                )
        );
    }

    @PutMapping("/{id}/toggle-active")
    public ResponseEntity<DeliveryPartner>
    toggleActiveStatus(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                deliveryPartnerService
                        .toggleActiveStatus(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>
    deleteDeliveryPartner(
            @PathVariable Long id) {

        deliveryPartnerService
                .deleteDeliveryPartner(id);

        return ResponseEntity.ok(
                "Delivery partner deleted successfully"
        );
    }
}