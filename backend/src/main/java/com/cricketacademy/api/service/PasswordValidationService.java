package com.cricketacademy.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Service for validating password strength and requirements
 */
@Service
@Slf4j
public class PasswordValidationService {

    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 128;

    private static final Pattern UPPERCASE_PATTERN = Pattern.compile("[A-Z]");
    private static final Pattern LOWERCASE_PATTERN = Pattern.compile("[a-z]");
    private static final Pattern DIGIT_PATTERN = Pattern.compile("[0-9]");
    private static final Pattern SPECIAL_CHAR_PATTERN = Pattern.compile("[^a-zA-Z0-9]");

    /**
     * Validate password strength and return detailed validation results
     */
    public PasswordValidationResult validatePassword(String password) {
        List<String> errors = new ArrayList<>();

        if (password == null || password.trim().isEmpty()) {
            errors.add("Password is required");
            return new PasswordValidationResult(false, errors);
        }

        // Check length
        if (password.length() < MIN_PASSWORD_LENGTH) {
            errors.add("Password must be at least " + MIN_PASSWORD_LENGTH + " characters long");
        }

        if (password.length() > MAX_PASSWORD_LENGTH) {
            errors.add("Password must not exceed " + MAX_PASSWORD_LENGTH + " characters");
        }

        // Check character requirements
        if (!UPPERCASE_PATTERN.matcher(password).find()) {
            errors.add("Password must contain at least one uppercase letter");
        }

        if (!LOWERCASE_PATTERN.matcher(password).find()) {
            errors.add("Password must contain at least one lowercase letter");
        }

        if (!DIGIT_PATTERN.matcher(password).find()) {
            errors.add("Password must contain at least one digit");
        }

        if (!SPECIAL_CHAR_PATTERN.matcher(password).find()) {
            errors.add("Password must contain at least one special character");
        }

        // Check for common weak patterns
        if (password.matches("(?i)password|123456|qwerty|admin|user")) {
            errors.add("Password is too common and easily guessable");
        }

        // Check for sequential characters
        if (password.matches(".*(012|123|234|345|456|567|678|789|890|abc|bcd|cde|def|efg|fgh|ghi|hij|ijk).*")) {
            errors.add("Password contains sequential characters");
        }

        // Check for repeated characters
        if (password.matches(".*(.)\\1{2,}.*")) {
            errors.add("Password contains too many repeated characters");
        }

        boolean isValid = errors.isEmpty();
        return new PasswordValidationResult(isValid, errors);
    }

    /**
     * Check if password is strong enough (basic check)
     */
    public boolean isPasswordStrong(String password) {
        return validatePassword(password).isValid();
    }

    /**
     * Generate a strong password suggestion
     */
    public String generatePasswordSuggestion() {
        String[] words = { "Cricket", "Academy", "Player", "Coach", "Training", "Skill", "Game", "Match" };
        String[] specials = { "!", "@", "#", "$", "%", "&", "*" };

        StringBuilder password = new StringBuilder();

        // Add random word
        password.append(words[(int) (Math.random() * words.length)]);

        // Add random number
        password.append((int) (Math.random() * 100));

        // Add special character
        password.append(specials[(int) (Math.random() * specials.length)]);

        // Add lowercase suffix
        password.append("aA");

        return password.toString();
    }

    /**
     * Result class for password validation
     */
    public static class PasswordValidationResult {
        private final boolean valid;
        private final List<String> errors;

        public PasswordValidationResult(boolean valid, List<String> errors) {
            this.valid = valid;
            this.errors = errors;
        }

        public boolean isValid() {
            return valid;
        }

        public List<String> getErrors() {
            return errors;
        }

        public String getErrorMessage() {
            return String.join("; ", errors);
        }
    }
}
