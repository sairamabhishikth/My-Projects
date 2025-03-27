package com.stockmarket.repository;

import com.stockmarket.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    // Additional query methods can be added here if needed
}
