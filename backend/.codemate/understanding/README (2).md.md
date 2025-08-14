# Cricket Academy API — High-Level Documentation

## Overview

The Cricket Academy API is a Spring Boot-based backend system designed to handle registrations, authentications, and user management for a cricket academy. It provides secure, RESTful endpoints to enable registration, login, user profile management, and administrative functions.

---

## Architecture & Major Components

- **Spring Boot**: Main server framework.
- **Spring Data JPA**: Abstraction over database operations (primarily MySQL).
- **Spring Security**: Handles user authentication, authorization, password encryption, and role-based access.
- **Maven**: Project/build manager.
- **Lombok**: Reduces Java boilerplate (getters, setters, etc.).

**Project Structure**
- **Controllers**: Define the REST API endpoints.
- **Services**: Business logic (like registration, profile updates).
- **Repositories**: Handle persistence to MySQL.
- **Entities and DTOs**: Represent database tables and API data objects.
- **Config**: Security and application-level configuration.
- **Exception Handling**: Unified custom exception responses.

---

## Key Features

### 1. User Registration & Authentication
- Register new users (students, coaches, or admins) with fields such as name, email, phone, age, experience level, and password.
- Secure passwords (BCrypt hashing).
- Authentication endpoints for login, registration, and health check.

### 2. Profile & User Management
- Endpoints to fetch, update, or deactivate user profiles.
- Fetch users by ID, experience level, or get all users (admins only).
- Statistics endpoint for user analytics (admin only).
- Consistency in API responses with timestamps and success/error messages.

### 3. Experience Level Management
- Enumerated types for user experience level: Beginner, Intermediate, Advanced, Professional.
- Endpoint to query all available experience levels.

### 4. Validation Utilities
- Dedicated endpoints to check whether an email or phone number is already taken before submitting registration.

### 5. Security & Access Control
- Role-based access (Student, Coach, Admin).
- Input validation is enforced for all endpoints. 
- CORS configuration supports cross-origin requests for integration with frontends.

### 6. Error Handling & Response Structure
- Standardized API responses for both success and error cases.
- HTTP status codes follow REST conventions (200, 201, 400, 401, etc.).
- Global exception handling for clear and consistent error messaging.

---

## Data Model Highlights

**User Entity**
- ID, Name, Email, Phone, Age
- Experience Level (enum)
- Password (hashed)
- User Role (enum: Student, Coach, Admin)
- Created/Updated timestamps
- Active/Inactive flag

---

## Usage & Interaction

- **Endpoints** `/api/auth/*` for authentication and registration logic.
- **Endpoints** `/api/users/*` for user management (profile fetching, updating, listing).
- API expects and responds with JSON.
- Some endpoints require authentication, with admin-only restrictions documented.

---

## Development & Deployment

- Supports standard Maven tasks (test, build, run).
- Runs on Java 17+, connects to MySQL 8+.
- Application properties are set via `application.yml`.

---

## Policies

- **Security**: All sensitive operations require authentication; passwords are hashed.
- **Validation**: Enforced at the API level using Bean Validation annotations.
- **Extensibility**: Designed for maintainability — DTOs for API interfaces, entities for data persistence.

---

## How to Get Started

1. Set up the MySQL database and configure `application.yml`.
2. Build and run the project using Maven.
3. Consume the documented endpoints for user registration, authentication, user management, etc.

---

## Licensing & Contribution

- MIT License
- Open to contributions via PRs, with guidelines for branching, testing, and submitting improvements.

---

**Summary:**  
The Cricket Academy API provides a secure, modern, and robust foundation for managing cricket academy users, leveraging best practices in Java REST API construction, authentication, role management, and extensible design.