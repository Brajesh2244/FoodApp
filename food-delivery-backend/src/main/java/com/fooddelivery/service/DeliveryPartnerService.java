package com.fooddelivery.service;

import com.fooddelivery.dto.DeliveryPartnerRequest;
import com.fooddelivery.entity.DeliveryPartner;
import com.fooddelivery.entity.DeliveryPartnerStatus;
import com.fooddelivery.repository.DeliveryPartnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryPartnerService {

    private final DeliveryPartnerRepository deliveryPartnerRepository;

    public DeliveryPartnerService(
            DeliveryPartnerRepository deliveryPartnerRepository) {

        this.deliveryPartnerRepository =
                deliveryPartnerRepository;
    }

    public DeliveryPartner createDeliveryPartner(
            DeliveryPartnerRequest request) {

        if (deliveryPartnerRepository
                .findByEmail(request.getEmail())
                .isPresent()) {

            throw new RuntimeException(
                    "Email already registered"
            );
        }

        if (deliveryPartnerRepository
                .findByPhone(request.getPhone())
                .isPresent()) {

            throw new RuntimeException(
                    "Phone already registered"
            );
        }

        DeliveryPartner partner =
                new DeliveryPartner();

        partner.setFullName(request.getFullName());
        partner.setPhone(request.getPhone());
        partner.setEmail(request.getEmail());
        partner.setPassword(request.getPassword());
        partner.setVehicleType(request.getVehicleType());
        partner.setVehicleNumber(request.getVehicleNumber());

        return deliveryPartnerRepository.save(partner);
    }

    public DeliveryPartner getDeliveryPartnerById(Long id) {

        return deliveryPartnerRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Delivery partner not found"
                        ));
    }

    public List<DeliveryPartner> getAllDeliveryPartners() {

        return deliveryPartnerRepository.findAll();
    }

    public List<DeliveryPartner> getAvailableDeliveryPartners() {

        return deliveryPartnerRepository.findByStatus(
                DeliveryPartnerStatus.AVAILABLE
        );
    }

    public DeliveryPartner updateDeliveryPartner(
            Long id,
            DeliveryPartnerRequest request) {

        DeliveryPartner partner =
                getDeliveryPartnerById(id);

        partner.setFullName(request.getFullName());
        partner.setPhone(request.getPhone());
        partner.setEmail(request.getEmail());
        partner.setVehicleType(request.getVehicleType());
        partner.setVehicleNumber(request.getVehicleNumber());

        return deliveryPartnerRepository.save(partner);
    }

    public DeliveryPartner updateStatus(
            Long id,
            DeliveryPartnerStatus status) {

        DeliveryPartner partner =
                getDeliveryPartnerById(id);

        partner.setStatus(status);

        return deliveryPartnerRepository.save(partner);
    }

    public DeliveryPartner toggleActiveStatus(Long id) {

        DeliveryPartner partner =
                getDeliveryPartnerById(id);

        partner.setActive(!partner.isActive());

        return deliveryPartnerRepository.save(partner);
    }

    public void deleteDeliveryPartner(Long id) {

        DeliveryPartner partner =
                getDeliveryPartnerById(id);

        deliveryPartnerRepository.delete(partner);
    }
}