package com.cricketacademy.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "career_applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CareerApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100)
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Email is required")
    @Email
    @Column(nullable = false)
    private String email;

    @NotBlank(message = "Phone number is required")
    @Column(nullable = false)
    private String phone;

    @NotBlank(message = "Position type is required")
    @Column(nullable = false)
    private String positionType; // Coach or Ground Staff

    @Column(length = 1000)
    private String qualifications;

    @Column(length = 1000)
    private String experience;

    @Column(length = 500)
    private String availability;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status = ApplicationStatus.PENDING;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public enum ApplicationStatus {
        PENDING,
        APPROVED,
        REJECTED
    }
}
