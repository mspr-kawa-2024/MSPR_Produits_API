package com.epsi.MSPR.controller;

import com.epsi.MSPR.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/database")
public class DatabaseController {

    @Autowired
    private DatabaseService databaseService;

    @GetMapping("/check-connection")
    public ResponseEntity<String> checkDatabaseConnection() {
        boolean isConnected = databaseService.isDatabaseConnected();
        if (isConnected) {
            return ResponseEntity.ok("Database connection is successful!");
        } else {
            return ResponseEntity.status(500).body("Failed to connect to the database.");
        }
    }
}
