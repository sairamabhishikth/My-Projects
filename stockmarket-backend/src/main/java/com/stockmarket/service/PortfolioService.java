package com.stockmarket.service;

import com.stockmarket.model.Portfolio;
import com.stockmarket.model.Stock;
import com.stockmarket.repository.PortfolioRepository;
import com.stockmarket.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final StockRepository stockRepository;

    public PortfolioService(PortfolioRepository portfolioRepository, StockRepository stockRepository) {
        this.portfolioRepository = portfolioRepository;
        this.stockRepository = stockRepository;
    }

    public List<Portfolio> getAllPortfolios() {
        return portfolioRepository.findAll();
    }

    public Portfolio getPortfolioById(Long id) {
        return portfolioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Portfolio not found!"));
    }

    @Transactional
    public Portfolio createPortfolio(Portfolio portfolio) {
        Stock stock = portfolio.getStock();
        if (stock.getStockId() == null) {
            // Save the stock if it's a new (transient) entity
            stock = stockRepository.save(stock);
            portfolio.setStock(stock);
        }
        return portfolioRepository.save(portfolio);
    }

    @Transactional
    public Portfolio updatePortfolio(Long id, Portfolio updatedPortfolio) {
        Portfolio existingPortfolio = getPortfolioById(id);

        Stock updatedStock = updatedPortfolio.getStock();
        if (updatedStock.getStockId() == null) {
            // Save the stock if it's a new (transient) entity
            updatedStock = stockRepository.save(updatedStock);
        }

        // Update portfolio properties
        existingPortfolio.setStock(updatedStock);
        existingPortfolio.setQuantity(updatedPortfolio.getQuantity());
        existingPortfolio.setAveragePurchasePrice(updatedPortfolio.getAveragePurchasePrice());
        existingPortfolio.setLatestPurchasePrice(updatedPortfolio.getLatestPurchasePrice());

        return portfolioRepository.save(existingPortfolio);
    }

    public void deletePortfolio(Long id) {
        portfolioRepository.deleteById(id);
    }

    public List<Portfolio> getPortfoliosByUserId(Long userId) {
        return portfolioRepository.findByUser_UserId(userId);
    }
}
