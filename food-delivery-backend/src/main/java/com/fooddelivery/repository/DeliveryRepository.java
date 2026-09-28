package com.fooddelivery.repository;

import com.fooddelivery.entity.Delivery;
import com.fooddelivery.entity.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    Optional<Delivery> findByOrderId(Long orderId);

    List<Delivery> findByDeliveryPartnerId(Long deliveryPartnerId);

    List<Delivery> findByStatus(DeliveryStatus status);

    List<Delivery> findByDeliveryPartnerIdAndStatus(
            Long deliveryPartnerId,
            DeliveryStatus status
    );
}