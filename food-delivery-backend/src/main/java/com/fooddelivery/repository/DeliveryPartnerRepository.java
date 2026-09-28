package com.fooddelivery.repository;

import com.fooddelivery.entity.DeliveryPartner;
import com.fooddelivery.entity.DeliveryPartnerStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryPartnerRepository
        extends JpaRepository<DeliveryPartner, Long> {

    Optional<DeliveryPartner> findByEmail(String email);

    Optional<DeliveryPartner> findByPhone(String phone);

    List<DeliveryPartner> findByStatus(
            DeliveryPartnerStatus status
    );

    List<DeliveryPartner> findByActiveTrue();
}