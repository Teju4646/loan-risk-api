package com.riskapi.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "risk_evaluations")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RiskEvaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private Applicant applicant;

    private Integer riskScore;
    private String decision; // APPROVE, REJECT, MANUAL_REVIEW

    @Column(length = 1000)
    private String reasoning;

    @Column(updatable = false)
    private Instant evaluatedAt;

    @PrePersist
    void onCreate() {
        this.evaluatedAt = Instant.now();
    }
}
