package com.cricketacademy.api.runner;

import com.cricketacademy.api.util.PasswordMigrationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Component;

/**
 * Runner to automatically migrate passwords on application startup
 * Runs after database migrations are complete
 */
@Component
@Slf4j
@Order(100) // Run after database initialization
public class PasswordMigrationRunner implements CommandLineRunner {

    private final PasswordMigrationUtil passwordMigrationUtil;

    public PasswordMigrationRunner(PasswordMigrationUtil passwordMigrationUtil) {
        this.passwordMigrationUtil = passwordMigrationUtil;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting automatic password migration...");

        try {
            int migratedCount = passwordMigrationUtil.migratePlainTextPasswords();
            if (migratedCount > 0) {
                log.info("Successfully migrated {} plain text passwords to BCrypt hashing", migratedCount);
            } else {
                log.info("No plain text passwords found - all passwords are properly hashed");
            }
        } catch (InvalidDataAccessResourceUsageException e) {
            if (e.getMessage().contains("Table") && e.getMessage().contains("not found")) {
                log.warn("Users table not found - skipping password migration. This is normal for a fresh database.");
            } else {
                log.error("Database access error during password migration", e);
            }
        } catch (Exception e) {
            log.error("Error during password migration", e);
        }
    }
}
