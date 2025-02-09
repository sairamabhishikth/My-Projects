package com.stockmarket.service;

import com.stockmarket.model.*;
import com.stockmarket.repository.*;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final StockRepository stockRepository;
    private final PortfolioRepository portfolioRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository,
                               StockRepository stockRepository, PortfolioRepository portfolioRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.stockRepository = stockRepository;
        this.portfolioRepository = portfolioRepository;
    }

    @Transactional
    public void buyStock(Long userId, Long stockId, int quantity, BigDecimal purchasePrice) {
        validateInputs(userId, stockId, quantity, purchasePrice);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new EntityNotFoundException("Stock not found!"));

        BigDecimal totalCost = purchasePrice.multiply(BigDecimal.valueOf(quantity));
        if (user.getNetWorth().compareTo(totalCost.doubleValue()) < 0) {
            throw new IllegalStateException("Insufficient funds!");
        }

        user.setNetWorth(user.getNetWorth() - totalCost.doubleValue());
        userRepository.save(user);

        Portfolio portfolio = portfolioRepository.findByUserAndStock(user, stock)
                .orElse(new Portfolio(user, stock, 0, BigDecimal.ZERO, BigDecimal.ZERO));
        int updatedQuantity = portfolio.getQuantity() + quantity;
        BigDecimal newTotalCost = portfolio.getAveragePurchasePrice()
                .multiply(BigDecimal.valueOf(portfolio.getQuantity()))
                .add(totalCost);
        portfolio.setQuantity(updatedQuantity);
        portfolio.setAveragePurchasePrice(newTotalCost.divide(BigDecimal.valueOf(updatedQuantity), 2, RoundingMode.HALF_UP));
        portfolio.setLatestPurchasePrice(purchasePrice);
        portfolioRepository.save(portfolio);

        Transaction transaction = new Transaction(user, stock, quantity, purchasePrice, TransactionType.BUY);
        transactionRepository.save(transaction);
    }

    @Transactional
    public void sellStock(Long userId, Long stockId, int quantity, BigDecimal sellPrice) {
        validateInputs(userId, stockId, quantity, sellPrice);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new EntityNotFoundException("Stock not found!"));

        Portfolio portfolio = portfolioRepository.findByUserAndStock(user, stock)
                .orElseThrow(() -> new EntityNotFoundException("Stock not found in portfolio!"));

        if (portfolio.getQuantity() < quantity) {
            throw new IllegalStateException("Insufficient stock quantity!");
        }

        BigDecimal totalProceeds = sellPrice.multiply(BigDecimal.valueOf(quantity));
        user.setNetWorth(user.getNetWorth() + totalProceeds.doubleValue());
        userRepository.save(user);

        portfolio.setQuantity(portfolio.getQuantity() - quantity);
        if (portfolio.getQuantity() == 0) {
            portfolioRepository.delete(portfolio);
        } else {
            portfolioRepository.save(portfolio);
        }

        Transaction transaction = new Transaction(user, stock, quantity, sellPrice, TransactionType.SELL);
        transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findBySoftDeletedFalse();
    }

    public List<Transaction> getTransactionsByUser(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID provided.");
        }

        List<Transaction> transactions = transactionRepository.findByUser_UserIdAndSoftDeletedFalse(userId);
        if (transactions.isEmpty()) {
            throw new EntityNotFoundException("No transactions found for user ID: " + userId);
        }
        return transactions;
    }

    public List<Transaction> getTransactionsByStock(Long stockId) {
        if (stockId == null || stockId <= 0) {
            throw new IllegalArgumentException("Invalid stock ID provided.");
        }

        return transactionRepository.findByStock_StockIdAndSoftDeletedFalse(stockId);
    }

    @Transactional
    public void softDeleteTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found"));
        transaction.setSoftDeleted(true);
        transactionRepository.save(transaction);
    }

    @Transactional
    public void reverseTransaction(Long transactionId) {
        Transaction originalTransaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found"));

        Transaction reversalTransaction = new Transaction(
                originalTransaction.getUser(),
                originalTransaction.getStock(),
                -originalTransaction.getQuantity(),
                originalTransaction.getPrice(),
                TransactionType.REVERSAL
        );
        transactionRepository.save(reversalTransaction);
    }

    public Page<Transaction> getTransactionsByUserId(Long userId, int page, int size) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID provided.");
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Transaction> transactions = transactionRepository.findAllByUser_UserId(userId, pageable);

        if (transactions.isEmpty()) {
            throw new EntityNotFoundException("No transactions found for user ID: " + userId);
        }

        return transactions;
    }

    private void validateInputs(Long userId, Long stockId, int quantity, BigDecimal price) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID.");
        }
        if (stockId == null || stockId <= 0) {
            throw new IllegalArgumentException("Invalid stock ID.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0.");
        }
    }
}
