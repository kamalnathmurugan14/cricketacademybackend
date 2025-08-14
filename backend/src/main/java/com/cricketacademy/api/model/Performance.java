package com.cricketacademy.api.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "performance_model")
public class Performance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "player_id")
    private com.cricketacademy.api.entity.User player;
    @Enumerated(EnumType.STRING)
    private Category category; // district, tnca, state
    private Integer matches;
    private Integer runs;
    private Integer wickets;
    private Double rating;
    private LocalDate date;

    public enum Category {
        district, tnca, state
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public com.cricketacademy.api.entity.User getPlayer() {
        return player;
    }

    public void setPlayer(com.cricketacademy.api.entity.User player) {
        this.player = player;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getMatches() {
        return matches;
    }

    public void setMatches(Integer matches) {
        this.matches = matches;
    }

    public Integer getRuns() {
        return runs;
    }

    public void setRuns(Integer runs) {
        this.runs = runs;
    }

    public Integer getWickets() {
        return wickets;
    }

    public void setWickets(Integer wickets) {
        this.wickets = wickets;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
