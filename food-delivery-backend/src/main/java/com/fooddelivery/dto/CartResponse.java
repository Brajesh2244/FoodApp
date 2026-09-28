package com.fooddelivery.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class CartResponse {

    private Long id;
    private int totalItems;
    private BigDecimal totalAmount;
    private List<CartItemResponse> items;
    private LocalDateTime updatedAt;

    public CartResponse() {
    }

    public CartResponse(Long id, int totalItems, BigDecimal totalAmount, List<CartItemResponse> items, LocalDateTime updatedAt) {
        this.id = id;
        this.totalItems = totalItems;
        this.totalAmount = totalAmount;
        this.items = items;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<CartItemResponse> getItems() {
        return items;
    }

    public void setItems(List<CartItemResponse> items) {
        this.items = items;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}