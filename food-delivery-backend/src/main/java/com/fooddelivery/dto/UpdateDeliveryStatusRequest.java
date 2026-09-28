package com.fooddelivery.dto;

import com.fooddelivery.entity.DeliveryStatus;

public class UpdateDeliveryStatusRequest {

    private DeliveryStatus status;

    public UpdateDeliveryStatusRequest() {
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }
}