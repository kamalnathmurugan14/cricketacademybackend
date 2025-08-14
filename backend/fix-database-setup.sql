-- Fix Database Setup Script
-- This script will create the missing users table and resolve the configuration conflict

-- Step 1: Create the database (run this in MySQL)
CREATE DATABASE IF NOT EXISTS cricket_academy;
USE cricket_academy;

-- Step 2: Create the users table (from V0__create_users_table.sql)
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20) NOT NULL,
    age INTEGER NOT NULL,
    experience_level VARCHAR(20) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'STUDENT',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT chk_age CHECK (age >= 5 AND age <= 80)
);

-- Create indexes
CREATE INDEX IF NOT EXISTS idx_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_role ON users(role);
CREATE INDEX IF NOT EXISTS idx_is_active ON users(is_active);
CREATE INDEX IF NOT EXISTS idx_created_at ON users(created_at);

-- Step 3: Create other required tables
CREATE TABLE IF NOT EXISTS career_applications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    age INT,
    experience_level VARCHAR(50),
    preferred_role VARCHAR(100),
    batting_style VARCHAR(50),
    bowling_style VARCHAR(50),
    additional_info TEXT,
    status VARCHAR(50) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS user_activity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    logout_time TIMESTAMP,
    ip_address VARCHAR(45),
    user_agent TEXT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS password_reset_tokens (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    token VARCHAR(255) NOT NULL UNIQUE,
    expiry_date TIMESTAMP NOT NULL,
    used BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Step 4: Insert initial data (from data.sql)
INSERT INTO users (name, email, password, age, phone, experience_level, email_verified, role, is_active) 
VALUES 
('Admin User', 'admin@cricketacademy.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBaUKk7h.T0mUO', 30, '1234567890', 'EXPERT', true, 'ADMIN', true),
('John Doe', 'john@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBaUKk7h.T0mUO', 25, '9876543210', 'INTERMEDIATE', true, 'USER', true),
('Jane Smith', 'jane@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBaUKk7h.T0mUO', 22, '5551234567', 'BEGINNER', true, 'USER', true),
('Mike Johnson', 'mike@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBaUKk7h.T0mUO', 28, '4449876543', 'ADVANCED', true, 'USER', true);

-- Verify the setup
SHOW TABLES;
SELECT * FROM users LIMIT 5;
