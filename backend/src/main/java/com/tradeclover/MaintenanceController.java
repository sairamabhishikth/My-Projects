package com.tradeclover;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MaintenanceController {

    // This endpoint checks if the app is in maintenance mode
    @GetMapping("/api/maintenance-status")
    public boolean maintenanceStatus() {
        // Set to true when in maintenance mode
        return true;  // For now, return true to simulate maintenance
    }

    // This endpoint serves the maintenance page
    @GetMapping("/maintenance")
    public String maintenancePage() {
        return "<h1>Under Maintenance</h1><p>Please check back later.</p>";
    }
}
