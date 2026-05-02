package com.rendezvous.demo.Service;

import java.util.UUID;

public interface TestResultService
{
    void cancelTestResult(UUID testResultId, UUID examinerId);
}
