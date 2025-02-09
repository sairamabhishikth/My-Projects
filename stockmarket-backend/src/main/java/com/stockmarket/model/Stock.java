package com.stockmarket.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "stocks")
public class Stock {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "stock_id") // Explicitly map to the "stock_id" column
	    private Long stockId;

	    @Column(name = "name", nullable = false, length = 255)
	    private String name;

	    @Column(name = "symbol", nullable = false, length = 255)
	    private String symbol;

	    @Column(name = "current_price", nullable = false, precision = 38, scale = 2)
	    private BigDecimal currentPrice;

	    @Column(name = "market_cap", precision = 38, scale = 2)
	    private BigDecimal marketCap;

	    // Getters and Setters
	    public Long getStockId() {
	        return stockId;
	    }

	    public void setStockId(Long stockId) {
	        this.stockId = stockId;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getSymbol() {
	        return symbol;
	    }

	    public void setSymbol(String symbol) {
	        this.symbol = symbol;
	    }

	    public BigDecimal getCurrentPrice() {
	        return currentPrice;
	    }

	    public void setCurrentPrice(BigDecimal currentPrice) {
	        this.currentPrice = currentPrice;
	    }

	    public BigDecimal getMarketCap() {
	        return marketCap;
	    }

	    public void setMarketCap(BigDecimal marketCap) {
	        this.marketCap = marketCap;
	    }
	}

