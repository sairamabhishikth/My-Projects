package com.stockmarket.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class BuyRequest {

    @NotNull(message = "User ID is required.")
    private Long userId;

    @NotNull(message = "Stock ID is required.")
    private Long stockId;

    @Positive(message = "Quantity must be greater than 0.")
    private int quantity;

    @NotNull(message = "Purchase price is required.")
    @DecimalMin(value = "0.01", inclusive = false, message = "Purchase price must be greater than 0.")
    private BigDecimal purchasePrice;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
}
