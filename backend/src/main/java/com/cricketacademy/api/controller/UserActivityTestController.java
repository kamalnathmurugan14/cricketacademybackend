package com.cricketacademy.api.controller;

import com.cricketacademy.api.dto.ApiResponse;
import com.cricketacademy.api.service.UserActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test/user-activity")
@RequiredArgsConstructor
public class UserActivityTestController {

    private final UserActivityService userActivityService;

    /**
     * Test endpoint to verify user activity logging
     */
    @PostMapping("/test-login")
    public ApiResponse<String> testLogin(@RequestParam Long userId, @RequestParam String ipAddress) {
        userActivityService.logLogin(userId, ipAddress, "test-agent", "test-session");
        return ApiResponse.success("Test login logged successfully");
    }

    /**
     * Test endpoint to verify user activity logging
     */
    @PostMapping("/test-logout")
    public ApiResponse<String> testLogout(@RequestParam Long userId, @RequestParam String ipAddress) {
        userActivityService.logLogout(userId, ipAddress, "test-agent", "test-session");
        return ApiResponse.success("Test logout logged successfully");
    }

    /**
     * Test endpoint to verify user activity logging
     */
    @PostMapping("/test-failed-login")
    public ApiResponse<String> testFailedLogin(@RequestParam Long userId, @RequestParam String ipAddress) {
        userActivityService.logFailedLogin(userId, ipAddress, "test-agent", "test-reason");
        return ApiResponse.success("Test failed login logged successfully");
    }

    /**
     * Test endpoint to verify user activity logging
     */
    @PostMapping("/test-password-change")
    public ApiResponse<String> testPasswordChange(@RequestParam Long userId, @RequestParam String ipAddress) {
        userActivityService.logPasswordChange(userId, ipAddress, "test-agent");
        return ApiResponse.success("Test password change logged successfully");
    }

    /**
     * Test endpoint to verify user activity logging
     */
    @GetMapping("/test-health")
    public ApiResponse<String> testHealth() {
        return ApiResponse.success("User activity system is healthy");
    }
}
