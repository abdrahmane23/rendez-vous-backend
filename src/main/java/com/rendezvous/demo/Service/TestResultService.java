package com.rendezvous.demo.Service;

import com.rendezvous.demo.domain.Dtos.TestResultResponseDto;
import com.rendezvous.demo.domain.Entities.TestResult;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public interface TestResultService
{
    void cancelTestResult(UUID testResultId, UUID examinerId);

     List<TestResult> getTestResultsForPatient(UUID patientId);

    TestResult getTestResultById(UUID testResultId);

    List<TestResult> getTestResultsForExaminer(UUID patientId);
}
