package com.cricketacademy.api.model;

import jakarta.persistence.*;

@Entity(name = "UserModel")
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
    @Column(name = "age")
    private Integer age;
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;
    @Enumerated(EnumType.STRING)
    @Column(name = "experience_level")
    private ExperienceLevel experienceLevel;

    public enum Role {
        STUDENT, COACH, ADMIN
    }

    public enum Category {
        kid, teen, adult
    }

    public enum ExperienceLevel {
        BEGINNER, INTERMEDIATE, ADVANCED, PROFESSIONAL
    }

    private String password;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "created_at", nullable = false, updatable = false)
    private java.time.LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private java.time.LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = java.time.LocalDateTime.now();
        updatedAt = java.time.LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = java.time.LocalDateTime.now();
    }

    @Column(name = "email_verified")
    private Boolean emailVerified;
    @Column(name = "phone_verified")
    private Boolean phoneVerified;
    @Column(name = "email_verification_pending")
    private String emailVerificationPending;
    @Column(name = "phone_verification_pending")
    private String phoneVerificationPending;

    // Getters and Setters
    public enum UserRole {
        STUDENT, COACH, ADMIN
    }

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ExperienceLevel getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(ExperienceLevel experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public java.time.LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.time.LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public java.time.LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(java.time.LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public Boolean getPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(Boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    public String getEmailVerificationPending() {
        return emailVerificationPending;
    }

    public void setEmailVerificationPending(String emailVerificationPending) {
        this.emailVerificationPending = emailVerificationPending;
    }

    public String getPhoneVerificationPending() {
        return phoneVerificationPending;
    }

    public void setPhoneVerificationPending(String phoneVerificationPending) {
        this.phoneVerificationPending = phoneVerificationPending;
    }
}
