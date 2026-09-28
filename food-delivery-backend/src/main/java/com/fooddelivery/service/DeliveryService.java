package com.fooddelivery.service;

import com.fooddelivery.dto.AssignDeliveryRequest;
import com.fooddelivery.entity.*;
import com.fooddelivery.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final FoodOrderRepository orderRepository;
    private final DeliveryPartnerRepository deliveryPartnerRepository;

    public DeliveryService(
            DeliveryRepository deliveryRepository,
            FoodOrderRepository orderRepository,
            DeliveryPartnerRepository deliveryPartnerRepository) {

        this.deliveryRepository = deliveryRepository;
        this.orderRepository = orderRepository;
        this.deliveryPartnerRepository = deliveryPartnerRepository;
    }

    @Transactional
    public Delivery assignDelivery(
            AssignDeliveryRequest request) {

        FoodOrder order = orderRepository
                .findById(request.getOrderId())
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        if (deliveryRepository
                .findByOrderId(order.getId())
                .isPresent()) {

            throw new RuntimeException(
                    "Delivery partner already assigned to this order"
            );
        }

        DeliveryPartner partner =
                deliveryPartnerRepository
                        .findById(request.getDeliveryPartnerId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Delivery partner not found"
                                ));

        if (!partner.isActive()) {
            throw new RuntimeException(
                    "Delivery partner is not active"
            );
        }

        if (partner.getStatus()
                != DeliveryPartnerStatus.AVAILABLE) {

            throw new RuntimeException(
                    "Delivery partner is not available"
            );
        }

        Delivery delivery = new Delivery();

        delivery.setOrder(order);
        delivery.setDeliveryPartner(partner);
        delivery.setStatus(DeliveryStatus.ASSIGNED);

        Delivery savedDelivery =
                deliveryRepository.save(delivery);

        // Partner becomes busy
        partner.setStatus(
                DeliveryPartnerStatus.BUSY
        );

        deliveryPartnerRepository.save(partner);

        // Order moves to confirmed
        order.setStatus(
                OrderStatus.CONFIRMED
        );

        orderRepository.save(order);

        return savedDelivery;
    }

    public Delivery getDeliveryById(Long id) {

        return deliveryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Delivery not found"
                        ));
    }

    public Delivery getDeliveryByOrderId(Long orderId) {

        return deliveryRepository.findByOrderId(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Delivery not found for this order"
                        ));
    }

    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    public List<Delivery> getDeliveriesByPartner(
            Long deliveryPartnerId) {

        return deliveryRepository
                .findByDeliveryPartnerId(
                        deliveryPartnerId
                );
    }

    @Transactional
    public Delivery updateDeliveryStatus(
            Long deliveryId,
            DeliveryStatus status) {

        Delivery delivery =
                getDeliveryById(deliveryId);

        delivery.setStatus(status);

        FoodOrder order = delivery.getOrder();

        switch (status) {

            case PICKED_UP:

                delivery.setPickedUpAt(
                        LocalDateTime.now()
                );

                order.setStatus(
                        OrderStatus.PREPARING
                );

                break;

            case OUT_FOR_DELIVERY:

                delivery.setOutForDeliveryAt(
                        LocalDateTime.now()
                );

                order.setStatus(
                        OrderStatus.OUT_FOR_DELIVERY
                );

                break;

            case DELIVERED:

                delivery.setDeliveredAt(
                        LocalDateTime.now()
                );

                order.setStatus(
                        OrderStatus.DELIVERED
                );

                DeliveryPartner partner =
                        delivery.getDeliveryPartner();

                partner.setStatus(
                        DeliveryPartnerStatus.AVAILABLE
                );

                deliveryPartnerRepository.save(partner);

                break;

            case CANCELLED:

                order.setStatus(
                        OrderStatus.CANCELLED
                );

                DeliveryPartner cancelledPartner =
                        delivery.getDeliveryPartner();

                cancelledPartner.setStatus(
                        DeliveryPartnerStatus.AVAILABLE
                );

                deliveryPartnerRepository
                        .save(cancelledPartner);

                break;

            default:
                break;
        }

        orderRepository.save(order);

        return deliveryRepository.save(delivery);
    }
}