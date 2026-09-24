package com.rendezvous.demo.Controller;


import com.rendezvous.demo.Mapper.TestResultMapper;
import com.rendezvous.demo.Service.TestResultService;
import com.rendezvous.demo.Util;
import com.rendezvous.demo.domain.Dtos.TestResultDetailsResponseDto;
import com.rendezvous.demo.domain.Dtos.TestResultResponseDto;
import com.rendezvous.demo.domain.Entities.TestResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/test-result")
@RequiredArgsConstructor
public class TestResultController {
    private final TestResultService testResultService;
    private final TestResultMapper testResultMapper;
    @PutMapping("/{testResultId}/cancel")
    public ResponseEntity<Void> cancelTestResult(@AuthenticationPrincipal Jwt jwt,
                                           @PathVariable UUID testResultId){
       UUID examinerId = Util.parseUserId(jwt);
       testResultService.cancelTestResult(testResultId, examinerId);
       return ResponseEntity.ok().build();
    }
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<TestResultResponseDto>> getTestResultsForPatient(
            @PathVariable UUID patientId
    ){
        List<TestResult> testResults = testResultService.getTestResultsForPatient(patientId);
        return ResponseEntity.ok(
                testResults.stream().map(testResultMapper::toTestResultResponseDto).toList()
        );
    }
    @GetMapping("/{testResultId}")
    public ResponseEntity<TestResultDetailsResponseDto> getTestResultById(
            @PathVariable UUID testResultId
    ){
        TestResult testResult = testResultService.getTestResultById(testResultId);
        return ResponseEntity.ok(testResultMapper.toTestResultDetailsResponseDto(testResult));
    }
    @GetMapping
    public ResponseEntity<List<TestResultDetailsResponseDto>> getTestResultsForExaminer(
            @AuthenticationPrincipal Jwt jwt
    ){
        UUID patientId = Util.parseUserId(jwt);
        List<TestResult> testResults = testResultService.getTestResultsForExaminer(patientId);
        return ResponseEntity.ok(
                testResults.stream().map(testResultMapper::toTestResultDetailsResponseDto).toList()
        );
    }
}
