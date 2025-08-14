package com.cricketacademy.api.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "fee_model")
public class Fee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "player_id")
    private com.cricketacademy.api.entity.User player;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private Status status; // paid, pending
    private LocalDate dueDate;
    private LocalDate paidDate;

    public enum Status {
        paid, pending
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(LocalDate paidDate) {
        this.paidDate = paidDate;
    }
}
