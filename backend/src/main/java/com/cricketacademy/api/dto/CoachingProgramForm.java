package com.cricketacademy.api.dto;

/**
 * Simple DTO for CoachingProgram JSON payloads.
 * Mirrors fields expected by the Admin UI.
 */
public class CoachingProgramForm {
    private String programName;
    private String description;
    private Integer hoursOfCoaching;
    private String daysAndTime;
    private Double amountPerMonth;
    private String targetAudience;
    private Boolean available;

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
}