package com.stockmarket.repository;

import com.stockmarket.model.Portfolio;
import com.stockmarket.model.Stock;
import com.stockmarket.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findByUser_UserId(Long userId); // Use userId, not id
    List<Portfolio> findByStock_StockId(Long stockId);
    Optional<Portfolio> findByUserAndStock(User user, Stock stock);
}
