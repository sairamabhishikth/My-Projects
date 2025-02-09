package com.stockmarket.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class SellRequest {

    @NotNull(message = "User ID is required.")
    private Long userId;

    @NotNull(message = "Stock ID is required.")
    private Long stockId;

    @Min(value = 1, message = "Quantity must be at least 1.")
    private int quantity;

    @NotNull(message = "Sell price is required.")
    @DecimalMin(value = "0.01", inclusive = false, message = "Sell price must be greater than 0.")
    private BigDecimal sellPrice;

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

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }
}
