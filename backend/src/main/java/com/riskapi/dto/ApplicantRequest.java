package com.riskapi.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ApplicantRequest {
    @NotBlank
    private String fullName;

    @Positive
    private Double annualIncome;

    @Min(300) @Max(850)
    private Integer creditScore;

    @PositiveOrZero
    private Double existingMonthlyDebt;

    @Positive
    private Double requestedLoanAmount;

    @PositiveOrZero
    private Integer employmentYears;

    @NotBlank
    private String employmentStatus;
}
