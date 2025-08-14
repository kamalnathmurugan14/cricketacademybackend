# Cricket Academy API - High-Level Documentation

## Overview

The Cricket Academy API is a Spring Boot-based backend system designed to manage user registration, authentication, and user management for a cricket academy. It exposes RESTful endpoints and incorporates security, validation, and role-based access control.

---

## Architecture & Technologies

- **Spring Boot**: Main application framework.
- **Spring Data JPA**: Data persistence.
- **Spring Security**: Authentication/authorization.
- **MySQL**: Relational database.
- **Lombok**: Reduces Java boilerplate.
- **Maven**: Build and dependency management.

The project is organized according to standard Spring Boot project structure, separating controllers, services, repositories, entities, DTOs, exceptions, and configuration.

---

## Core Features

### 1. **User Registration & Authentication**
- **Register**: Allows new users to sign up by providing basic details (name, email, phone, age, experience, password).
- **Authentication**: Supports user authentication mechanisms (Spring Security).
- **Validation**: Endpoints to check email and phone availability.
- **Password Security**: Passwords are safely hashed using BCrypt.

### 2. **Experience Levels**
- Supports multiple experience levels: **BEGINNER**, **INTERMEDIATE**, **ADVANCED**, **PROFESSIONAL**.

### 3. **User Management**
- Retrieve own profile.
- Retrieve any user by ID.
- List all (active) users (admin-only).
- Filter users by experience level.
- Update user details.
- Deactivate (soft delete) user accounts.

### 4. **Statistics**
- Admins can fetch user statistics for overview and reporting.

### 5. **Error Handling**
- Consistent error response format with descriptive messages and timestamps.
- Handles common HTTP errors: validation errors, not found, unauthorized access, conflicts, internal errors.

### 6. **Security**
- Role-based access control among **STUDENT**, **COACH**, and **ADMIN** roles.
- CORS (Cross-origin) support for client integration.
- Validation enforced for all user input (Bean Validation).

---

## Data Model (Simplified)
- **User**: Main entity; holds registration and profile data, including role and experience level.
- **ExperienceLevel**: Enumerated types for user proficiency.
- **UserRole**: Enumerated types for access control.

---

## API Highlights

- **/api/auth/register**: Registers new users.
- **/api/auth/health**: Health check endpoint.
- **/api/auth/experience-levels**: Fetches supported experience levels.
- **/api/auth/validate-email**, **/validate-phone**: Checks for uniqueness.
- **/api/users/**: Endpoints for profile retrieval, user management, and statistics.

---

## Setup & Operation

- Requires Java 17+, Maven, and MySQL.
- Configuration is managed via `application.yml`.
- Project is built and run via Maven.

---

## Testing & Deployment

- Automated tests can be run via Maven.
- Deployment is as a standalone jar.

---

## Contribution & Licensing

- Open for contributions (fork & pull request workflow).
- Licensed under MIT License.

---

## Summary

The Cricket Academy API provides a robust backend for a sports academy registration and management system, featuring user registration with validation, secure authentication, admin/user/coach roles, detailed error handling, and extendable architecture designed for easy integration and future enhancement.