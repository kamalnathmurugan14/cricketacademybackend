package com.cricketacademy.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity for tracking user login and logout activities
 */
@Entity
@Table(name = "user_activity")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "login_time", nullable = false)
    private LocalDateTime loginTime;

    @Column(name = "logout_time")
    private LocalDateTime logoutTime;

    @Column(name = "session_active", nullable = false)
    private Boolean sessionActive = true;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "user_agent")
    private String userAgent;

    @Enumerated(EnumType.STRING)
    @Column(name = "activity_type")
    private ActivityType activityType;

    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "additional_info", columnDefinition = "TEXT")
    private String additionalInfo;

    public enum ActivityType {
        LOGIN, LOGOUT, LOGIN_FAILED, PASSWORD_CHANGED
    }

    // Constructor for creating new login session
    public UserActivity(User user, LocalDateTime loginTime, String ipAddress, String userAgent) {
        this.user = user;
        this.loginTime = loginTime;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.sessionActive = true;
    }

    // Method to mark session as logged out
    public void logout(LocalDateTime logoutTime) {
        this.logoutTime = logoutTime;
        this.sessionActive = false;
    }
} 