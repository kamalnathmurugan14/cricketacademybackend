package com.cricketacademy.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/debug")
public class DebugController {

    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> testEndpoint() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Debug endpoint is accessible");
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/security-info")
    public ResponseEntity<Map<String, Object>> securityInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Security debugging endpoint");
        response.put("allowedMethods", "GET, POST, PUT, DELETE, OPTIONS");
        response.put("corsEnabled", true);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/echo")
    public ResponseEntity<Map<String, Object>> echo(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("received", request);
        response.put("message", "Request received successfully");
        return ResponseEntity.ok(response);
    }
}
