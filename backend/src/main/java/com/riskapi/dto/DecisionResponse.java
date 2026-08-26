package com.riskapi.dto;

import lombok.*;

@Data @Builder
public class DecisionResponse {
    private Long applicantId;
    private Integer riskScore;
    private String decision;
    private String reasoning;
}
