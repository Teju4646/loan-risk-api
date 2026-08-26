package com.riskapi.service;

import com.riskapi.dto.*;
import com.riskapi.exception.ResourceNotFoundException;
import com.riskapi.model.*;
import com.riskapi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final RiskEvaluationRepository evaluationRepository;
    private final RiskScoringService riskScoringService;

    @Transactional
    public DecisionResponse submitAndEvaluate(ApplicantRequest req) {
        Applicant applicant = Applicant.builder()
                .fullName(req.getFullName())
                .annualIncome(req.getAnnualIncome())
                .creditScore(req.getCreditScore())
                .existingMonthlyDebt(req.getExistingMonthlyDebt())
                .requestedLoanAmount(req.getRequestedLoanAmount())
                .employmentYears(req.getEmploymentYears())
                .employmentStatus(req.getEmploymentStatus())
                .build();
        applicant = applicantRepository.save(applicant);

        var result = riskScoringService.evaluate(req);

        RiskEvaluation evaluation = RiskEvaluation.builder()
                .applicant(applicant)
                .riskScore(result.score())
                .decision(result.decision())
                .reasoning(result.reasoning())
                .build();
        evaluationRepository.save(evaluation);

        return DecisionResponse.builder()
                .applicantId(applicant.getId())
                .riskScore(result.score())
                .decision(result.decision())
                .reasoning(result.reasoning())
                .build();
    }

    public List<RiskEvaluation> getHistory(Long applicantId) {
        if (!applicantRepository.existsById(applicantId)) {
            throw new ResourceNotFoundException("Applicant not found: " + applicantId);
        }
        return evaluationRepository.findByApplicantIdOrderByEvaluatedAtDesc(applicantId);
    }

    public Applicant getApplicant(Long id) {
        return applicantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Applicant not found: " + id));
    }
}
