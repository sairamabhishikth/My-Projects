package com.stockmarket.controller;

import com.stockmarket.dto.BuyRequest;
import com.stockmarket.dto.SellRequest;
import com.stockmarket.model.Transaction;
import com.stockmarket.service.TransactionService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private static final Logger log = LoggerFactory.getLogger(TransactionController.class);

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/buy")
    public ResponseEntity<?> buyStock(@Valid @RequestBody BuyRequest buyRequest, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            transactionService.buyStock(
                    buyRequest.getUserId(),
                    buyRequest.getStockId(),
                    buyRequest.getQuantity(),
                    buyRequest.getPurchasePrice()
            );
            return ResponseEntity.ok("Stock purchased successfully.");
        } catch (RuntimeException ex) {
            log.error("Error during stock purchase: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to process buy request: " + ex.getMessage());
        }
    }

    @PostMapping("/sell")
    public ResponseEntity<?> sellStock(@Valid @RequestBody SellRequest sellRequest, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            transactionService.sellStock(
                    sellRequest.getUserId(),
                    sellRequest.getStockId(),
                    sellRequest.getQuantity(),
                    sellRequest.getSellPrice()
            );
            return ResponseEntity.ok("Stock sold successfully.");
        } catch (RuntimeException ex) {
            log.error("Error during stock sale: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to process sell request: " + ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/user/{userId}/all")
    public ResponseEntity<?> getUserTransactions(@PathVariable Long userId) {
        try {
            List<Transaction> transactions = transactionService.getTransactionsByUser(userId);
            return ResponseEntity.ok(transactions);
        } catch (IllegalArgumentException | EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/stock/{stockId}")
    public ResponseEntity<?> getStockTransactions(@PathVariable Long stockId) {
        try {
            List<Transaction> transactions = transactionService.getTransactionsByStock(stockId);
            return ResponseEntity.ok(transactions);
        } catch (IllegalArgumentException | EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/{id}/reverse")
    public ResponseEntity<?> reverseTransaction(@PathVariable Long id) {
        try {
            transactionService.reverseTransaction(id);
            return ResponseEntity.ok("Transaction reversed successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error while reversing transaction with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to reverse transaction.");
        }
    }

    @DeleteMapping("/{id}/soft-delete")
    public ResponseEntity<?> softDeleteTransaction(@PathVariable Long id) {
        try {
            transactionService.softDeleteTransaction(id);
            return ResponseEntity.ok("Transaction soft-deleted successfully.");
        } catch (RuntimeException ex) {
            log.error("Error during soft delete: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to soft-delete transaction: " + ex.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getTransactionsByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<Transaction> transactions = transactionService.getTransactionsByUserId(userId, page, size);
            return ResponseEntity.ok(transactions);
        } catch (IllegalArgumentException ex) {
            // Return a detailed error response for bad requests
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", ex.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (EntityNotFoundException ex) {
            // Return a detailed error response for not found entities
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

}
