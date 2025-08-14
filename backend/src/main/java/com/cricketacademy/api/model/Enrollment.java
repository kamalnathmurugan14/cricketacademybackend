package com.cricketacademy.api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "enrollment_model")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long programId;

    @Column(nullable = false)
    private String paymentMethod; // "card", "upi", "cash"

    @Column(nullable = false)
    private String status; // "pending", "enrolled"

    private String programTitle;
    private String coachName;
}
