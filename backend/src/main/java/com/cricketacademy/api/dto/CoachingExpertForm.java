package com.cricketacademy.api.dto;

import org.springframework.web.multipart.MultipartFile;

/**
 * Form-backing DTO for creating/updating CoachingExpert via
 * multipart/form-data.
 * Matches the Admin UI fields.
 */
public class CoachingExpertForm {
    private String name;
    private String coachType;
    private String phone;
    private String email;
    private String bio;
    private Integer experience;
    private Double rating;
    private String profilePhotoUrl; // URL string from UI
    // Qualifications arrive as a JSON string array ["Level-1","Level-2"]
    private String qualifications;
    // Optional photo upload (not currently used by UI but kept for future)
    private MultipartFile profilePhoto;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoachType() {
        return coachType;
    }

    public void setCoachType(String coachType) {
        this.coachType = coachType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public MultipartFile getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(MultipartFile profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}