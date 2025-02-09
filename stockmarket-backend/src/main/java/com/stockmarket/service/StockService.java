package com.stockmarket.service;

import com.stockmarket.model.Stock;
import com.stockmarket.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private static final Logger logger = LoggerFactory.getLogger(StockService.class);

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<Stock> getAllStocks() {
        logger.info("Fetching all stocks...");
        return stockRepository.findAll();
    }

    public Stock getStockById(Long id) {
        logger.info("Fetching stock by ID: {}", id);
        return stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock not found with ID: " + id));
    }

    public Stock createStock(Stock stock) {
        if (stock.getStockId() != null) {
            throw new IllegalArgumentException("Stock ID must not be set manually!");
        }

        logger.info("Creating new stock: {}", stock.getName());
        return stockRepository.save(stock);
    }

    public Stock updateStock(Long id, Stock updatedStock) {
        Stock existingStock = getStockById(id);

        logger.info("Updating stock with ID: {}", id);

        existingStock.setSymbol(updatedStock.getSymbol());
        existingStock.setName(updatedStock.getName());
        existingStock.setCurrentPrice(updatedStock.getCurrentPrice());
        existingStock.setMarketCap(updatedStock.getMarketCap());

        return stockRepository.save(existingStock);
    }

    public void deleteStock(Long id) {
        Stock stock = getStockById(id);
        logger.info("Deleting stock with ID: {}", id);
        stockRepository.delete(stock);
    }
}
