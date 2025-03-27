package com.stockmarket.controller;

import com.stockmarket.dto.LoginRequest;
import com.stockmarket.dto.RegistrationRequest;
import com.stockmarket.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest request, BindingResult result) {
        // Collect and return validation errors, if any
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        String token = authService.registerUser(request.getUsername(), request.getPassword(), request.getNetWorth());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest request, BindingResult result) {
        // Collect and return validation errors, if any
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        // Proceed with login if validation passes
        String token = authService.loginUser(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(token);
    }
}
