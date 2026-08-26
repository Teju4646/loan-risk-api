package com.riskapi.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "applicants")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Applicant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private Double annualIncome;
    private Integer creditScore;
    private Double existingMonthlyDebt;
    private Double requestedLoanAmount;
    private Integer employmentYears;
    private String employmentStatus; // EMPLOYED, SELF_EMPLOYED, UNEMPLOYED

    @Column(updatable = false)
    private Instant createdAt;

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
    }
}
