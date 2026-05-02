package com.rendezvous.demo.Controller;

import com.rendezvous.demo.Service.ConsultationService;
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
@RequestMapping("api/consultation")
@RequiredArgsConstructor
public class ConsultationController {
    private final ConsultationService consultationService;
    @PutMapping("cancel/{consultationId}")
    public ResponseEntity<Void> cancelConsultation(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID consultationId
    ){
      UUID examinerId = Util.parseUserId(jwt);
      consultationService.cancelConsultation(examinerId, consultationId);
      return ResponseEntity.ok().build();


    }
}
