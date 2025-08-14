package com.cricketacademy.api.service;

import com.cricketacademy.api.dto.PasswordResetResponse;
import com.cricketacademy.api.entity.OtpToken;
import com.cricketacademy.api.repository.OtpTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpService {

    private final OtpTokenRepository otpTokenRepository;
    private final EmailService emailService;

    @Value("${app.otp.expiration-minutes:10}")
    private int otpExpirationMinutes;

    @Value("${app.otp.length:6}")
    private int otpLength;

    /**
     * Generate a secure OTP
     */
    public String generateOtp() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < otpLength; i++) {
            otp.append(random.nextInt(10));
        }

        return otp.toString();
    }

    /**
     * Generate OTP for a specific identifier
     */
    public String generateOtp(String identifier) {
        return generateOtp();
    }

    /**
     * Send OTP via email
     */
    public void sendOtpEmail(String email, String otp) {
        emailService.sendOtpEmail(email, otp);
    }

    /**
     * Send OTP via SMS
     */
    public void sendOtpSms(String phone, String otp) {
        // Implementation for SMS sending would go here
        log.info("Sending OTP via SMS to: {}", phone);
    }

    /**
     * Generate and send OTP for password reset
     */
    public PasswordResetResponse generateAndSendOtp(String email) {
        try {
            // Clean up any existing unused OTPs for this email
            otpTokenRepository.findByEmailAndUsedFalseAndExpiresAtAfter(email, LocalDateTime.now())
                    .ifPresent(existingOtp -> {
                        existingOtp.setUsed(true);
                        otpTokenRepository.save(existingOtp);
                    });

            // Generate new OTP
            String otp = generateOtp();

            // Create OTP token
            OtpToken otpToken = new OtpToken();
            otpToken.setEmail(email);
            otpToken.setOtp(otp);
            otpToken.setCreatedAt(LocalDateTime.now());
            otpToken.setExpiresAt(LocalDateTime.now().plusMinutes(otpExpirationMinutes));
            otpToken.setUsed(false);

            otpTokenRepository.save(otpToken);

            // Send OTP via email
            emailService.sendOtpEmail(email, otp);

            log.info("OTP generated and sent to: {}", email);
            return PasswordResetResponse.builder()
                    .success(true)
                    .message("OTP has been sent to your email")
                    .timestamp(LocalDateTime.now())
                    .email(email)
                    .build();

        } catch (Exception e) {
            log.error("Failed to generate and send OTP to: {}", email, e);
            return PasswordResetResponse.builder()
                    .success(false)
                    .message("Failed to send OTP. Please try again.")
                    .timestamp(LocalDateTime.now())
                    .email(email)
                    .build();
        }
    }

    /**
     * Validate OTP
     */
    public boolean validateOtp(String email, String otp) {
        Optional<OtpToken> otpToken = otpTokenRepository.findByEmailAndOtp(email, otp);

        if (otpToken.isEmpty()) {
            log.warn("Invalid OTP attempt for email: {}", email);
            return false;
        }

        OtpToken token = otpToken.get();

        if (token.isUsed()) {
            log.warn("OTP already used for email: {}", email);
            return false;
        }

        if (token.isExpired()) {
            log.warn("Expired OTP attempt for email: {}", email);
            return false;
        }

        // Mark OTP as used
        token.setUsed(true);
        otpTokenRepository.save(token);

        log.info("OTP validated successfully for email: {}", email);
        return true;
    }

    /**
     * Check if user has valid OTP
     */
    public boolean hasValidOtp(String email) {
        return otpTokenRepository.hasValidOtp(email, LocalDateTime.now());
    }

    /**
     * Clean up expired OTPs
     */
    public void cleanupExpiredOtps() {
        otpTokenRepository.deleteByExpiresAtBefore(LocalDateTime.now());
        log.info("Cleaned up expired OTPs");
    }
}
