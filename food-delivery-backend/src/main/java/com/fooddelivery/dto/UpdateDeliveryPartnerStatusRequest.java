package com.fooddelivery.dto;

import com.fooddelivery.entity.DeliveryPartnerStatus;

public class UpdateDeliveryPartnerStatusRequest {

    private DeliveryPartnerStatus status;

    public UpdateDeliveryPartnerStatusRequest() {
    }

    public DeliveryPartnerStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryPartnerStatus status) {
        this.status = status;
    }
}