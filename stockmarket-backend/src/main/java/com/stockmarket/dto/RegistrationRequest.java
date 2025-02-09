package com.stockmarket.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class RegistrationRequest {

    @NotBlank(message = "Username is required.")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters.")
    private String username;

    @NotBlank(message = "Password is required.")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters.")
    private String password;

    @Positive(message = "Net worth must be positive.")
    @DecimalMin(value = "0.01", inclusive = true, message = "Net worth must be at least 0.01.")
    private double netWorth;

    // Default Constructor
    public RegistrationRequest() {
    }

    // Parameterized Constructor (Optional for testing or manual instantiation)
    public RegistrationRequest(String username, String password, double netWorth) {
        this.username = username;
        this.password = password;
        this.netWorth = netWorth;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getNetWorth() {
        return netWorth;
    }

    public void setNetWorth(double netWorth) {
        this.netWorth = netWorth;
    }
}
