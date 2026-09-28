package com.fooddelivery.dto;

import com.fooddelivery.entity.PaymentStatus;

public class UpdatePaymentStatusRequest {

    private PaymentStatus paymentStatus;

    public UpdatePaymentStatusRequest() {
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}