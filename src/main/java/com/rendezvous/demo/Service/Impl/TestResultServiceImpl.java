package com.rendezvous.demo.Service.Impl;


import com.rendezvous.demo.Exception.UserNotFoundException;
import com.rendezvous.demo.Repositories.TestResultRepo;
import com.rendezvous.demo.Repositories.UserRepo;
import com.rendezvous.demo.Service.TestResultService;
import com.rendezvous.demo.domain.Entities.Appointement;
import com.rendezvous.demo.domain.Entities.TestResult;
import com.rendezvous.demo.domain.Entities.User;
import com.rendezvous.demo.domain.Enums.AppointementStatus;
import com.rendezvous.demo.domain.Enums.TestResultStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TestResultServiceImpl implements TestResultService {
    private final UserRepo userRepo;
    private final TestResultRepo testResultRepo;

    @Override
    @Transactional
    public void cancelTestResult(UUID testResultId,UUID examinerId) {
        User examiner= userRepo.findById(examinerId).orElseThrow(
                ()-> new UserNotFoundException("Examiner not found")
        );

        TestResult testResult = testResultRepo.findById(testResultId).orElseThrow(
                ()-> new RuntimeException("test result not found")
        );
        Appointement appointement = testResult.getAppointement();
        if (!appointement.getExaminer().equals(examiner)){
            throw new RuntimeException("You are not authorized to cancel this consultation");
        }
        if (testResult.getStatus().equals(TestResultStatus.CANCELED)){
            throw new RuntimeException("This testResult is already canceled");
        };
        appointement.setStatus(AppointementStatus.COMPLETED);
        testResult.setStatus(TestResultStatus.CANCELED);


    }
}
