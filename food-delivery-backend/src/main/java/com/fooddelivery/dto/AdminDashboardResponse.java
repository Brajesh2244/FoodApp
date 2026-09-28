package com.fooddelivery.dto;

import java.math.BigDecimal;

public class AdminDashboardResponse {

    private long totalUsers;
    private long totalRestaurants;
    private long totalCategories;
    private long totalFoodItems;
    private long totalOrders;
    private long pendingOrders;
    private long confirmedOrders;
    private long preparingOrders;
    private long outForDeliveryOrders;
    private long deliveredOrders;
    private long cancelledOrders;

    private long totalDeliveryPartners;
    private long availableDeliveryPartners;
    private long busyDeliveryPartners;

    private BigDecimal totalRevenue;

    public AdminDashboardResponse() {
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalRestaurants() {
        return totalRestaurants;
    }

    public void setTotalRestaurants(long totalRestaurants) {
        this.totalRestaurants = totalRestaurants;
    }

    public long getTotalCategories() {
        return totalCategories;
    }

    public void setTotalCategories(long totalCategories) {
        this.totalCategories = totalCategories;
    }

    public long getTotalFoodItems() {
        return totalFoodItems;
    }

    public void setTotalFoodItems(long totalFoodItems) {
        this.totalFoodItems = totalFoodItems;
    }

    public long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public long getPendingOrders() {
        return pendingOrders;
    }

    public void setPendingOrders(long pendingOrders) {
        this.pendingOrders = pendingOrders;
    }

    public long getConfirmedOrders() {
        return confirmedOrders;
    }

    public void setConfirmedOrders(long confirmedOrders) {
        this.confirmedOrders = confirmedOrders;
    }

    public long getPreparingOrders() {
        return preparingOrders;
    }

    public void setPreparingOrders(long preparingOrders) {
        this.preparingOrders = preparingOrders;
    }

    public long getOutForDeliveryOrders() {
        return outForDeliveryOrders;
    }

    public void setOutForDeliveryOrders(long outForDeliveryOrders) {
        this.outForDeliveryOrders = outForDeliveryOrders;
    }

    public long getDeliveredOrders() {
        return deliveredOrders;
    }

    public void setDeliveredOrders(long deliveredOrders) {
        this.deliveredOrders = deliveredOrders;
    }

    public long getCancelledOrders() {
        return cancelledOrders;
    }

    public void setCancelledOrders(long cancelledOrders) {
        this.cancelledOrders = cancelledOrders;
    }

    public long getTotalDeliveryPartners() {
        return totalDeliveryPartners;
    }

    public void setTotalDeliveryPartners(long totalDeliveryPartners) {
        this.totalDeliveryPartners = totalDeliveryPartners;
    }

    public long getAvailableDeliveryPartners() {
        return availableDeliveryPartners;
    }

    public void setAvailableDeliveryPartners(long availableDeliveryPartners) {
        this.availableDeliveryPartners = availableDeliveryPartners;
    }

    public long getBusyDeliveryPartners() {
        return busyDeliveryPartners;
    }

    public void setBusyDeliveryPartners(long busyDeliveryPartners) {
        this.busyDeliveryPartners = busyDeliveryPartners;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}