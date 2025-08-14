package com.cricketacademy.api.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity(name = "CareerApplicationModel")
@Table(name = "career_application_model")
public class CareerApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String formData;
    @Enumerated(EnumType.STRING)
    private Status status; // in_progress, progress_next, appointed
    private LocalDate appliedDate;
    private Boolean validatedByAdmin;
    private String notes;
    private String positionType;

    public String getPositionType() {
        return positionType;
    }

    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }

    public enum Status {
        IN_PROGRESS, PROGRESS_NEXT, APPOINTED
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFormData() {
        return formData;
    }

    public void setFormData(String formData) {
        this.formData = formData;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(LocalDate appliedDate) {
        this.appliedDate = appliedDate;
    }

    public Boolean getValidatedByAdmin() {
        return validatedByAdmin;
    }

    public void setValidatedByAdmin(Boolean validatedByAdmin) {
        this.validatedByAdmin = validatedByAdmin;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
