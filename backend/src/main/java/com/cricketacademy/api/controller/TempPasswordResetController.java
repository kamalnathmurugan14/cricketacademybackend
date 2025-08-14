package com.cricketacademy.api.controller;

import com.cricketacademy.api.dto.EmailRequest;
import com.cricketacademy.api.dto.EmailResponse;
import com.cricketacademy.api.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Temporary Password Reset Controller
 * Bypasses database operations for immediate email functionality
 */
@RestController
@RequestMapping("/api/temp-password-reset")
@CrossOrigin(origins = { "http://localhost:5173", "http://localhost:3000" })
@RequiredArgsConstructor
@Slf4j
public class TempPasswordResetController {

    private final EmailService emailService;

    /**
     * Send password reset email without database operations
     * This is a temporary solution to bypass database issues
     */
    @PostMapping("/send-email")
    public ResponseEntity<Map<String, Object>> sendPasswordResetEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        try {
            log.info("Sending temporary password reset email to: {}", email);

            // Generate a simple OTP
            String otp = generateSimpleOtp();

            // Create email content
            String subject = "Password Reset Code - Cricket Academy";
            String body = "Dear User,\n\n" +
                    "Your password reset verification code is: " + otp + "\n\n" +
                    "This code is valid for 10 minutes.\n\n" +
                    "Please use this code to reset your password.\n\n" +
                    "If you did not request this password reset, please ignore this email.\n\n" +
                    "Best regards,\n" +
                    "Cricket Academy Team";

            // Send email directly
            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setTo(email);
            emailRequest.setSubject(subject);
            emailRequest.setBody(body);

            EmailResponse emailResponse = emailService.sendSimpleEmail(emailRequest);

            if (emailResponse.isSuccess()) {
                log.info("Password reset email sent successfully to: {}", email);
                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "message", "Password reset code has been sent to your email",
                        "timestamp", LocalDateTime.now(),
                        "email", email,
                        "otp", otp // Remove this in production!
                ));
            } else {
                log.error("Failed to send password reset email to: {}", email);
                return ResponseEntity.badRequest().body(Map.of(
                        "success", false,
                        "message", "Failed to send password reset email",
                        "timestamp", LocalDateTime.now(),
                        "email", email));
            }

        } catch (Exception e) {
            log.error("Error sending password reset email to: {}", email, e);
            return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "message", "An error occurred while sending the password reset email",
                    "timestamp", LocalDateTime.now(),
                    "email", email));
        }
    }

    /**
     * Generate a simple 6-digit OTP
     */
    private String generateSimpleOtp() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            otp.append(random.nextInt(10));
        }

        return otp.toString();
    }

    /**
     * Test endpoint to verify the controller is working
     */
    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> test() {
        return ResponseEntity.ok(Map.of(
                "message", "Temporary password reset controller is working",
                "timestamp", LocalDateTime.now()));
    }
}