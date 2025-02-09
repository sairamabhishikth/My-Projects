package com.stockmarket.repository;

import com.stockmarket.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Find all transactions excluding soft-deleted ones
    List<Transaction> findBySoftDeletedFalse();

    // Find transactions by User ID excluding soft-deleted ones
    List<Transaction> findByUser_UserIdAndSoftDeletedFalse(Long userId);

    // Find transactions by Stock ID excluding soft-deleted ones
    List<Transaction> findByStock_StockIdAndSoftDeletedFalse(Long stockId);
    
 // Paginated retrieval of transactions by user ID
    Page<Transaction> findAllByUser_UserId(Long userId, Pageable pageable);

}
