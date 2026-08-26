package com.riskapi.service;

import com.riskapi.dto.ApplicantRequest;
import org.springframework.stereotype.Service;

@Service
public class RiskScoringService {

    public record ScoreResult(int score, String decision, String reasoning) {}

    public ScoreResult evaluate(ApplicantRequest req) {
        StringBuilder reasoning = new StringBuilder();
        int score = 50; // baseline

        // 1. Credit score band (max ±30)
        if (req.getCreditScore() >= 750) {
            score += 30;
            reasoning.append("Excellent credit score (+30). ");
        } else if (req.getCreditScore() >= 670) {
            score += 15;
            reasoning.append("Good credit score (+15). ");
        } else if (req.getCreditScore() >= 580) {
            score -= 10;
            reasoning.append("Fair credit score (-10). ");
        } else {
            score -= 30;
            reasoning.append("Poor credit score (-30). ");
        }

        // 2. Debt-to-income ratio (max ±25)
        double monthlyIncome = req.getAnnualIncome() / 12.0;
        double dti = req.getExistingMonthlyDebt() / monthlyIncome;
        if (dti < 0.2) {
            score += 25;
            reasoning.append("Low debt-to-income ratio (+25). ");
        } else if (dti < 0.36) {
            score += 10;
            reasoning.append("Acceptable debt-to-income ratio (+10). ");
        } else if (dti < 0.5) {
            score -= 15;
            reasoning.append("High debt-to-income ratio (-15). ");
        } else {
            score -= 30;
            reasoning.append("Very high debt-to-income ratio (-30). ");
        }

        // 3. Employment stability (max ±15)
        if ("UNEMPLOYED".equalsIgnoreCase(req.getEmploymentStatus())) {
            score -= 25;
            reasoning.append("Unemployed (-25). ");
        } else if (req.getEmploymentYears() >= 3) {
            score += 15;
            reasoning.append("Stable employment history (+15). ");
        } else if (req.getEmploymentYears() >= 1) {
            score += 5;
            reasoning.append("Moderate employment history (+5). ");
        } else {
            score -= 10;
            reasoning.append("Short employment history (-10). ");
        }

        // 4. Loan-to-income ratio (max ±15)
        double loanToIncome = req.getRequestedLoanAmount() / req.getAnnualIncome();
        if (loanToIncome < 0.3) {
            score += 15;
            reasoning.append("Conservative loan-to-income ratio (+15). ");
        } else if (loanToIncome < 0.6) {
            score += 0;
            reasoning.append("Moderate loan-to-income ratio (0). ");
        } else {
            score -= 20;
            reasoning.append("High loan-to-income ratio (-20). ");
        }

        score = Math.max(0, Math.min(100, score));

        String decision;
        if (score >= 70) decision = "APPROVE";
        else if (score >= 45) decision = "MANUAL_REVIEW";
        else decision = "REJECT";

        return new ScoreResult(score, decision, reasoning.toString().trim());
    }
}
