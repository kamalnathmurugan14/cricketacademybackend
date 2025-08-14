package com.cricketacademy.api.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity(name = "AttendanceModel")
@Table(name = "attendance_model")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private Type type; // player or coach
    @ManyToOne
    @JoinColumn(name = "user_id")
    private com.cricketacademy.api.entity.User user;
    private Boolean present;
    private String remarks;

    public enum Type {
        player, coach
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public com.cricketacademy.api.entity.User getUser() {
        return user;
    }

    public void setUser(com.cricketacademy.api.entity.User user) {
        this.user = user;
    }

    public Boolean getPresent() {
        return present;
    }

    public void setPresent(Boolean present) {
        this.present = present;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
