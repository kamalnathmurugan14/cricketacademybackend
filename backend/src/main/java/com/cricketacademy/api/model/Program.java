package com.cricketacademy.api.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "coaching_program")
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String programName;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer hoursOfCoaching;

    @Column(nullable = false)
    private String daysAndTime; // JSON string for days and time slots

    @Column(nullable = false)
    private Double amountPerMonth;

    @Column(nullable = false)
    private String targetAudience; // Kids, Teens, Adults, All

    @Column(nullable = false)
    private Boolean available = true;

    private LocalDate startDate;

    private LocalDate endDate;

    @Column(nullable = false)
    private LocalDate createdAt = LocalDate.now();

    @Column(nullable = false)
    private LocalDate updatedAt = LocalDate.now();

    @ManyToMany
    @JoinTable(name = "program_coaches", joinColumns = @JoinColumn(name = "program_id"), inverseJoinColumns = @JoinColumn(name = "coach_id"))
    private List<CoachingExpert> assignedCoaches;

    // Constructors
    public Program() {
    }

    public Program(String programName, String description, Integer hoursOfCoaching,
            String daysAndTime, Double amountPerMonth, String targetAudience) {
        this.programName = programName;
        this.description = description;
        this.hoursOfCoaching = hoursOfCoaching;
        this.daysAndTime = daysAndTime;
        this.amountPerMonth = amountPerMonth;
        this.targetAudience = targetAudience;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getHoursOfCoaching() {
        return hoursOfCoaching;
    }

    public void setHoursOfCoaching(Integer hoursOfCoaching) {
        this.hoursOfCoaching = hoursOfCoaching;
    }

    public String getDaysAndTime() {
        return daysAndTime;
    }

    public void setDaysAndTime(String daysAndTime) {
        this.daysAndTime = daysAndTime;
    }

    public Double getAmountPerMonth() {
        return amountPerMonth;
    }

    public void setAmountPerMonth(Double amountPerMonth) {
        this.amountPerMonth = amountPerMonth;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<CoachingExpert> getAssignedCoaches() {
        return assignedCoaches;
    }

    public void setAssignedCoaches(List<CoachingExpert> assignedCoaches) {
        this.assignedCoaches = assignedCoaches;
    }
}
