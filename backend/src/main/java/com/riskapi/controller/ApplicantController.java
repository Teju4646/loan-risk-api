package com.riskapi.controller;

import com.riskapi.dto.*;
import com.riskapi.model.RiskEvaluation;
import com.riskapi.service.ApplicantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/applicants")
@RequiredArgsConstructor
public class ApplicantController {

    private final ApplicantService applicantService;

    @PostMapping
    public DecisionResponse submit(@Valid @RequestBody ApplicantRequest request) {
        return applicantService.submitAndEvaluate(request);
    }

    @GetMapping("/{id}/history")
    public List<RiskEvaluation> history(@PathVariable Long id) {
        return applicantService.getHistory(id);
    }
}
