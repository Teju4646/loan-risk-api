package com.riskapi;

import com.riskapi.dto.ApplicantRequest;
import com.riskapi.service.RiskScoringService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RiskScoringServiceTest {

    private final RiskScoringService service = new RiskScoringService();

    @Test
    void highIncomeGoodCredit_shouldApprove() {
        ApplicantRequest req = new ApplicantRequest();
        req.setFullName("Jane Doe");
        req.setAnnualIncome(120000.0);
        req.setCreditScore(780);
        req.setExistingMonthlyDebt(500.0);
        req.setRequestedLoanAmount(20000.0);
        req.setEmploymentYears(5);
        req.setEmploymentStatus("EMPLOYED");

        var result = service.evaluate(req);
        assertEquals("APPROVE", result.decision());
        assertTrue(result.score() >= 70);
    }

    @Test
    void unemployedLowCredit_shouldReject() {
        ApplicantRequest req = new ApplicantRequest();
        req.setFullName("John Smith");
        req.setAnnualIncome(30000.0);
        req.setCreditScore(500);
        req.setExistingMonthlyDebt(1200.0);
        req.setRequestedLoanAmount(25000.0);
        req.setEmploymentYears(0);
        req.setEmploymentStatus("UNEMPLOYED");

        var result = service.evaluate(req);
        assertEquals("REJECT", result.decision());
    }
}
