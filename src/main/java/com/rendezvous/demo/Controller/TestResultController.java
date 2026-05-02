package com.rendezvous.demo.Controller;


import com.rendezvous.demo.Service.TestResultService;
import com.rendezvous.demo.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/test-result")
@RequiredArgsConstructor
public class TestResultController {
    private final TestResultService testResultService;
    @PutMapping("/{testResultId}/cancel")
    public ResponseEntity<Void> cancelTestResult(@AuthenticationPrincipal Jwt jwt,
                                           @PathVariable UUID testResultId){
       UUID examinerId = Util.parseUserId(jwt);
       testResultService.cancelTestResult(testResultId, examinerId);
       return ResponseEntity.ok().build();
    }
}
