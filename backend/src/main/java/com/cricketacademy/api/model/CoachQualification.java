package com.cricketacademy.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "coach_qualifications")
public class CoachQualification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String qualificationName;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "coaching_expert_id", nullable = false)
    private CoachingExpert coachingExpert;

    // Constructors
    public CoachQualification() {
    }

    public CoachQualification(String qualificationName, String description, CoachingExpert coachingExpert) {
        this.qualificationName = qualificationName;
        this.description = description;
        this.coachingExpert = coachingExpert;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQualificationName() {
        return qualificationName;
    }

    public void setQualificationName(String qualificationName) {
        this.qualificationName = qualificationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CoachingExpert getCoachingExpert() {
        return coachingExpert;
    }

    public void setCoachingExpert(CoachingExpert coachingExpert) {
        this.coachingExpert = coachingExpert;
    }
}
