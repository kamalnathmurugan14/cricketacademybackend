package com.cricketacademy.api.util;

import com.cricketacademy.api.entity.User;
import com.cricketacademy.api.repository.UserRepository;
import com.cricketacademy.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * CLI utility to reset user passwords
 * Usage: Run with --reset-password=true --email=USER_EMAIL
 * --new-password=NEW_PASSWORD
 */
@Component
@Profile("dev")
@Slf4j
public class PasswordResetCLI implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public PasswordResetCLI(UserRepository userRepository, UserService userService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        boolean resetPassword = false;
        String email = null;
        String newPassword = null;

        // Parse command line arguments
        for (String arg : args) {
            if (arg.startsWith("--reset-password=")) {
                resetPassword = Boolean.parseBoolean(arg.split("=")[1]);
            } else if (arg.startsWith("--email=")) {
                email = arg.split("=")[1];
            } else if (arg.startsWith("--new-password=")) {
                newPassword = arg.split("=")[1];
            }
        }

        if (resetPassword && email != null && newPassword != null) {
            resetUserPassword(email, newPassword);
        }
    }

    private void resetUserPassword(String email, String newPassword) {
        try {
            Optional<User> userOpt = userRepository.findByEmail(email);
            if (userOpt.isEmpty()) {
                log.error("User not found with email: {}", email);
                return;
            }

            userService.resetUserPassword(email, newPassword);

            log.info("Password successfully reset for user: {}", email);
            log.info("New password hash: {}", passwordEncoder.encode(newPassword));

        } catch (Exception e) {
            log.error("Error resetting password for email: {}", email, e);
        }
    }

    /**
     * Display current password hash for debugging
     */
    public void displayPasswordHash(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            log.info("Current password hash for {}: {}", email, user.getPassword());
            log.info("Hash format: {}", user.getPassword().startsWith("$2a$") ? "BCrypt" : "Plain text");
        } else {
            log.error("User not found: {}", email);
        }
    }
}
