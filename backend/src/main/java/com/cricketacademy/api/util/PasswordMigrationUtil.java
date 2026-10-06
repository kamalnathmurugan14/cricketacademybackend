package com.cricketacademy.api.util;

import com.cricketacademy.api.entity.User;
import com.cricketacademy.api.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Utility to migrate plain text passwords to BCrypt hashing
 */
@Component
@Slf4j
public class PasswordMigrationUtil {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public PasswordMigrationUtil(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Migrate all plain text passwords to BCrypt hashing
     */
    public int migratePlainTextPasswords() {
        log.info("Starting password migration process...");

        try {
            // Check if users table exists by trying to count users
            long userCount = userRepository.count();
            log.debug("Found {} users in database", userCount);

            if (userCount == 0) {
                log.info("No users found in database - skipping password migration");
                return 0;
            }

            List<User> usersWithPlainTextPasswords = userRepository.findAll().stream()
                    .filter(user -> user.getPassword() != null && !user.getPassword().startsWith("$2a$"))
                    .toList();

            int migratedCount = 0;

            for (User user : usersWithPlainTextPasswords) {
                try {
                    String plainPassword = user.getPassword();
                    String hashedPassword = passwordEncoder.encode(plainPassword);

                    user.setPassword(hashedPassword);
                    userRepository.save(user);

                    migratedCount++;
                    log.info("Migrated password for user: {}", user.getEmail());

                } catch (Exception e) {
                    log.error("Failed to migrate password for user: {}", user.getEmail(), e);
                }
            }

            log.info("Password migration completed. Migrated {} passwords", migratedCount);
            return migratedCount;

        } catch (Exception e) {
            log.error("Error accessing user repository during password migration", e);
            throw e; // Re-throw to be handled by the runner
        }
    }

    /**
     * Validate password strength
     */
    public boolean isPasswordStrong(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        // Check for at least one uppercase, one lowercase, one digit, and one special
        // character
        boolean hasUppercase = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLowercase = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = password.chars().anyMatch(c -> !Character.isLetterOrDigit(c));

        return hasUppercase && hasLowercase && hasDigit && hasSpecial;
    }
}
