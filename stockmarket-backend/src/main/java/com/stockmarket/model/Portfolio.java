package com.stockmarket.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "portfolios")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long portfolioId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "average_purchase_price", precision = 18, scale = 2)
    private BigDecimal averagePurchasePrice;

    @Column(name = "latest_purchase_price", precision = 18, scale = 2)
    private BigDecimal latestPurchasePrice;

    // Default constructor for JPA
    public Portfolio() {
    }

    // Constructor for creating new Portfolio objects
    public Portfolio(User user, Stock stock, int quantity, BigDecimal averagePurchasePrice, BigDecimal latestPurchasePrice) {
        this.user = user;
        this.stock = stock;
        this.quantity = quantity;
        this.averagePurchasePrice = averagePurchasePrice;
        this.latestPurchasePrice = latestPurchasePrice;
    }

    // Getters and Setters
    public Long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Long portfolioId) {
        this.portfolioId = portfolioId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAveragePurchasePrice() {
        return averagePurchasePrice;
    }

    public void setAveragePurchasePrice(BigDecimal averagePurchasePrice) {
        this.averagePurchasePrice = averagePurchasePrice;
    }

    public BigDecimal getLatestPurchasePrice() {
        return latestPurchasePrice;
    }

    public void setLatestPurchasePrice(BigDecimal latestPurchasePrice) {
        this.latestPurchasePrice = latestPurchasePrice;
    }
}
