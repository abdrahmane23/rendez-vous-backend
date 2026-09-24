package com.rendezvous.demo.Controller;

import com.rendezvous.demo.Mapper.AppointementMapper;
import com.rendezvous.demo.Mapper.UserMapper;
import com.rendezvous.demo.Service.AppointementService;
import com.rendezvous.demo.Util;
import com.rendezvous.demo.domain.Dtos.GetAppointementResponseDto;
import com.rendezvous.demo.domain.Dtos.TimeSlotResponseDto;
import com.rendezvous.demo.domain.Dtos.GetExaminerForAppointementResponseDto;
import com.rendezvous.demo.domain.Entities.Appointement;
import com.rendezvous.demo.domain.Entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.rendezvous.demo.domain.Dtos.CreateConsultationRequestDto;
import com.rendezvous.demo.domain.Dtos.CreateTestResultRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/appointements")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class AppointementController {
        private final AppointementService appointementService;
        private final UserMapper userMapper;
        private final AppointementMapper appointementMapper;

        @PostMapping({ "sections/{sectionId}/{examinerId}" })
        public ResponseEntity<Void> createAppointement(
                        @PathVariable UUID sectionId,
                        @PathVariable UUID examinerId,
                        @RequestParam LocalDateTime startTime,
                        @AuthenticationPrincipal Jwt jwt) {

                UUID patientId = Util.parseUserId(jwt);
                appointementService.createAppointement(patientId, sectionId, examinerId, startTime);

                return ResponseEntity.ok().build();

        }

        @PostMapping({ "TestSections/{sectionId}" })
        public ResponseEntity<Void> createAppointementFortest(
                        @PathVariable UUID sectionId,
                        @RequestParam LocalDateTime startTime,
                        @AuthenticationPrincipal Jwt jwt) {
                UUID patientId = Util.parseUserId(jwt);
                appointementService.createAppointementForTest(patientId, sectionId, startTime);
                return ResponseEntity.ok().build();

        }

        @GetMapping("/sections/{sectionId}/available-examiners")
        public ResponseEntity<List<GetExaminerForAppointementResponseDto>> getAvailableExaminersForAppointement(
                        @PathVariable UUID sectionId,
                        @RequestParam LocalDateTime startTime) {
                List<User> availableExaminers = appointementService.getAvailableExaminersForAppointement(sectionId,
                                startTime);
                return ResponseEntity.ok(
                                availableExaminers.stream()
                                                .map(userMapper::toGetExaminerForAppointementResponseDto)
                                                .toList());
        }

        @GetMapping("/sections/{sectionId}/available-times/{examinerId}")
        public ResponseEntity<List<TimeSlotResponseDto>> getAvailableTimeSlotsForExaminerInSection(
                        @PathVariable UUID sectionId,
                        @PathVariable UUID examinerId) {
                List<TimeSlotResponseDto> availableTimeSlots = appointementService
                                .getAvailableTimeSlotsForExaminerInSection(sectionId, examinerId);
                System.out.println("Received request for available time slots for examiner " + examinerId + " in section " + sectionId);

                return ResponseEntity.ok(availableTimeSlots);
        }

        @GetMapping("/sections/{sectionId}/available-times")
        public ResponseEntity<Map<UUID, List<TimeSlotResponseDto>>> getAvailableTimeSlotsForSection(
                        @PathVariable UUID sectionId) {
                Map<UUID, List<TimeSlotResponseDto>> availableTimeSlots = appointementService
                                .getAvailableTimeSlotsForSection(sectionId);
                return ResponseEntity.ok(availableTimeSlots);
        }

        @GetMapping("/sections/{sectionId}/available-times-for-test")
        public ResponseEntity<List<TimeSlotResponseDto>> getAvailableTimeSlotsForTestSection(
                        @PathVariable UUID sectionId) {
                List<TimeSlotResponseDto> availableTimeSlots = appointementService
                                .getAvailableTimeSlotsForTestSection(sectionId);
                return ResponseEntity.ok(availableTimeSlots);
        }

        @GetMapping("/upcoming-appointements")
        public ResponseEntity<Page<GetAppointementResponseDto>> getUpcomingAppointementForPatient(
                        @AuthenticationPrincipal Jwt jwt,
                        Pageable pageable) {
                UUID patientId = Util.parseUserId(jwt);
                Page<Appointement> upcomingAppointements = appointementService
                                .getUpcomingAppointementsForPatient(patientId, pageable);
                return ResponseEntity.ok(upcomingAppointements.map(appointementMapper::toGetAppointementResponseDto));
        }

        @PutMapping("/{appointementId}")
        public ResponseEntity<Page<GetAppointementResponseDto>> cancelAppointement(
                        @AuthenticationPrincipal Jwt jwt,
                        @PathVariable UUID appointementId) {
                UUID patientId = Util.parseUserId(jwt);
                appointementService.cancelAppointement(patientId, appointementId);
                return ResponseEntity.ok().build();
        }

        @GetMapping("/shifts/{shiftId}")
        public ResponseEntity<List<GetAppointementResponseDto>> getAppointementsForShift(
                        @AuthenticationPrincipal Jwt jwt,
                        @PathVariable UUID shiftId) {
                UUID examinerId = Util.parseUserId(jwt);
                List<Appointement> appointements = appointementService.getAppointementsForShift(examinerId, shiftId);
                return ResponseEntity.ok(
                                appointements.stream()
                                                .map(appointementMapper::toGetAppointementResponseDto)
                                                .toList());
        }

        @PostMapping("/{appointementId}/complete")
        public ResponseEntity<Void> completeAppointement(
                        @AuthenticationPrincipal Jwt jwt,
                        @PathVariable UUID appointementId) {
                UUID examinerId = Util.parseUserId(jwt);
                appointementService.markAppointementAsCompleted(examinerId, appointementId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        @PostMapping("/{appointementId}/non-attended")
        public ResponseEntity<Void> markAppointementAsNonAttended(
                        @AuthenticationPrincipal Jwt jwt,
                        @PathVariable UUID appointementId) {
                UUID examinerId = Util.parseUserId(jwt);
                appointementService.markAppointementAsNonAttended(examinerId, appointementId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        @PostMapping("/{appointementId}/test-results")
        public ResponseEntity<Void> createTestResult(
                        @AuthenticationPrincipal Jwt jwt,
                        @PathVariable UUID appointementId,
                        @Valid @RequestBody CreateTestResultRequestDto requestDto) {
                UUID examinerId = Util.parseUserId(jwt);
                appointementService.createTestResult(examinerId, appointementId, requestDto);
                return new ResponseEntity<>(HttpStatus.CREATED);
        }

        @PostMapping("/{appointementId}/consultations")
        public ResponseEntity<Void> createConsultation(
                        @AuthenticationPrincipal Jwt jwt,
                        @PathVariable UUID appointementId,
                        @Valid @RequestBody CreateConsultationRequestDto requestDto) {
                UUID examinerId = Util.parseUserId(jwt);
                appointementService.createConsultation(examinerId, appointementId, requestDto);
                return new ResponseEntity<>(HttpStatus.CREATED);
        }

}
