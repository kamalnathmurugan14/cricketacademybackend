package com.cricketacademy.api.controller;

import com.cricketacademy.api.dto.EmailRequest;
import com.cricketacademy.api.dto.EmailResponse;
import com.cricketacademy.api.service.EmailService;
import com.cricketacademy.api.service.OtpService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/email")
@Slf4j
public class EmailController {

    private final EmailService emailService;

    @Autowired
    private OtpService otpService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<EmailResponse> sendEmail(@Valid @RequestBody EmailRequest emailRequest) {
        log.info("Received email send request to: {}", emailRequest.getTo());
        EmailResponse response = emailService.sendSimpleEmail(emailRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/send-html")
    public ResponseEntity<EmailResponse> sendHtmlEmail(@Valid @RequestBody EmailRequest emailRequest) {
        log.info("Received HTML email send request to: {}", emailRequest.getTo());
        EmailResponse response = emailService.sendHtmlEmail(emailRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/test")
    public ResponseEntity<EmailResponse> testEmail() {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo("test@example.com");
        emailRequest.setSubject("Test Email");
        emailRequest.setBody("This is a test email from Cricket Academy API");

        EmailResponse response = emailService.sendSimpleEmail(emailRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/test-otp")
    public ResponseEntity<Map<String, Object>> testOtpEmail(@RequestParam String email) {
        try {
            log.info("Testing OTP email send to: {}", email);

            // Generate OTP
            String otp = otpService.generateOtp(email);

            // Send OTP email
            otpService.sendOtpEmail(email, otp);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "OTP email sent successfully to " + email,
                    "otp", otp, // Remove this in production!
                    "timestamp", java.time.LocalDateTime.now()));
        } catch (Exception e) {
            log.error("Failed to send test OTP email to: {}", email, e);
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Failed to send OTP email: " + e.getMessage(),
                    "timestamp", java.time.LocalDateTime.now()));
        }
    }
}
