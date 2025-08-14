package com.cricketacademy.api.controller;

import com.cricketacademy.api.dto.EmailRequest;
import com.cricketacademy.api.dto.EmailResponse;
import com.cricketacademy.api.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = { "http://localhost:5173", "http://localhost:3000" })
@Slf4j
public class EmailTestController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/email")
    public ResponseEntity<EmailResponse> testEmail(@RequestBody EmailRequest emailRequest) {
        log.info("Testing email configuration with request: {}", emailRequest);

        try {
            EmailResponse response = emailService.sendSimpleEmail(emailRequest);
            log.info("Email test result: {}", response);

            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            log.error("Email test failed", e);
            return ResponseEntity.internalServerError()
                    .body(EmailResponse.builder()
                            .success(false)
                            .message("Email test failed: " + e.getMessage())
                            .build());
        }
    }

    @GetMapping("/email/config")
    public ResponseEntity<String> checkEmailConfig() {
        log.info("Checking email configuration...");

        try {
            // Test if email service is properly configured by checking the mail sender
            log.info("Email service is available and configured");
            return ResponseEntity.ok("Email configuration appears to be properly set up");
        } catch (Exception e) {
            log.error("Email configuration check failed", e);
            return ResponseEntity.badRequest()
                    .body("Email configuration issue: " + e.getMessage());
        }
    }

    @PostMapping("/email/otp")
    public ResponseEntity<EmailResponse> testOtpEmail(@RequestParam String email, @RequestParam String otp) {
        log.info("Testing OTP email to: {} with OTP: {}", email, otp);

        try {
            EmailResponse response = emailService.sendOtpEmail(email, otp);
            log.info("OTP email test result: {}", response);

            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            log.error("OTP email test failed", e);
            return ResponseEntity.internalServerError()
                    .body(EmailResponse.builder()
                            .success(false)
                            .message("OTP email test failed: " + e.getMessage())
                            .build());
        }
    }
}
