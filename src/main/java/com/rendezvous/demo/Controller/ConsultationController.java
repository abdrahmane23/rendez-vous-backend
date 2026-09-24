package com.rendezvous.demo.Controller;

import com.rendezvous.demo.Mapper.ConsultationMapper;
import com.rendezvous.demo.Service.ConsultationService;
import com.rendezvous.demo.Util;
import com.rendezvous.demo.domain.Dtos.ConsultationDetailsResponseDto;
import com.rendezvous.demo.domain.Dtos.ConsultationResponseDto;
import com.rendezvous.demo.domain.Dtos.TestResultResponseDto;
import com.rendezvous.demo.domain.Entities.Consultation;
import com.rendezvous.demo.domain.Entities.TestResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/consultation")
@RequiredArgsConstructor
public class ConsultationController {
    private final ConsultationService consultationService;
    private final ConsultationMapper consultationMapper;
    @PutMapping("cancel/{consultationId}")
    public ResponseEntity<Void> cancelConsultation(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID consultationId
    ){
      UUID examinerId = Util.parseUserId(jwt);
      consultationService.cancelConsultation(examinerId, consultationId);
      return ResponseEntity.ok().build();


    }
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<ConsultationResponseDto>> getTestResultsForPatient(
            @PathVariable UUID patientId
    ){
        List<Consultation> consultation = consultationService.getConsultationForPatient(patientId);
        return ResponseEntity.ok(
                consultation.stream().map(consultationMapper::toConsultationResponseDto).toList()
        );
    }
    @GetMapping("/{consultationId}")
    public ResponseEntity<ConsultationDetailsResponseDto> getTestResultById(
            @PathVariable UUID consultationId
    ){
        Consultation consultation = consultationService.getConsultationById(consultationId);
        return ResponseEntity.ok(consultationMapper.toConsultationDetailsResponseDto(consultation));
    }
    @GetMapping
    public ResponseEntity<List<ConsultationDetailsResponseDto>> getConsultationsForExaminer(
            @AuthenticationPrincipal Jwt jwt
    ){
        UUID examinerId = Util.parseUserId(jwt);
        List<Consultation> consultations = consultationService.getConsultationsForExaminer(examinerId);
        return ResponseEntity.ok(
                consultations.stream().map(consultationMapper::toConsultationDetailsResponseDto).toList()
        );
    }
}
