package com.riskapi.repository;

import com.riskapi.model.RiskEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RiskEvaluationRepository extends JpaRepository<RiskEvaluation, Long> {
    List<RiskEvaluation> findByApplicantIdOrderByEvaluatedAtDesc(Long applicantId);
}
