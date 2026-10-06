package com.cricketacademy.api.service;

import com.cricketacademy.api.dto.PasswordResetResponse;
import com.cricketacademy.api.entity.User;
import com.cricketacademy.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordResetService {

    private final OtpService otpService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Request password reset - generate and send OTP
     */
    public PasswordResetResponse requestPasswordReset(String email) {
        return otpService.generateAndSendOtp(email);
    }

    /**
     * Validate OTP
     */
    public PasswordResetResponse validateOtp(String email, String otp) {
        boolean isValid = otpService.validateOtp(email, otp);
        if (isValid) {
            return PasswordResetResponse.builder()
                    .success(true)
                    .message("OTP validated successfully")
                    .timestamp(LocalDateTime.now())
                    .email(email)
                    .build();
        } else {
            return PasswordResetResponse.builder()
                    .success(false)
                    .message("Invalid or expired OTP")
                    .timestamp(LocalDateTime.now())
                    .email(email)
                    .build();
        }
    }

    /**
     * Reset password after OTP validation
     */
    public PasswordResetResponse resetPassword(String email, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            return PasswordResetResponse.builder()
                    .success(false)
                    .message("Passwords do not match")
                    .timestamp(LocalDateTime.now())
                    .email(email)
                    .build();
        }

        if (!otpService.hasValidOtp(email)) {
            return PasswordResetResponse.builder()
                    .success(false)
                    .message("No valid OTP found")
                    .timestamp(LocalDateTime.now())
                    .email(email)
                    .build();
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        otpService.cleanupExpiredOtps();

        return PasswordResetResponse.builder()
                .success(true)
                .message("Password reset successfully")
                .timestamp(LocalDateTime.now())
                .email(email)
                .build();
    }
}
