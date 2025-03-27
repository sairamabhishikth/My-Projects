package com.stockmarket.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;
    
    @Column(name = "soft_deleted", nullable = false)
    private boolean softDeleted = false; // Default to false


    public boolean isSoftDeleted() {
		return softDeleted;
	}

	public void setSoftDeleted(boolean softDeleted) {
		this.softDeleted = softDeleted;
	}

	@Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    // Default constructor for JPA
    public Transaction() {
    }

    // Constructor for creating new transactions
    public Transaction(User user, Stock stock, int quantity, BigDecimal price, TransactionType type) {
        this.user = user;
        this.stock = stock;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
        this.transactionDate = LocalDateTime.now(); // Automatically set the transaction date
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}
