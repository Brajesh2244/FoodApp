package com.fooddelivery.dto;

import java.math.BigDecimal;

public class CartItemResponse {

    private Long id;
    private Long foodItemId;
    private String foodItemName;
    private String foodItemImageUrl;
    private int quantity;
    private BigDecimal price;
    private BigDecimal subtotal;

    public CartItemResponse() {
    }

    public CartItemResponse(Long id, Long foodItemId, String foodItemName, String foodItemImageUrl, int quantity, BigDecimal price, BigDecimal subtotal) {
        this.id = id;
        this.foodItemId = foodItemId;
        this.foodItemName = foodItemName;
        this.foodItemImageUrl = foodItemImageUrl;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = subtotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFoodItemId() {
        return foodItemId;
    }

    public void setFoodItemId(Long foodItemId) {
        this.foodItemId = foodItemId;
    }

    public String getFoodItemName() {
        return foodItemName;
    }

    public void setFoodItemName(String foodItemName) {
        this.foodItemName = foodItemName;
    }

    public String getFoodItemImageUrl() {
        return foodItemImageUrl;
    }

    public void setFoodItemImageUrl(String foodItemImageUrl) {
        this.foodItemImageUrl = foodItemImageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}