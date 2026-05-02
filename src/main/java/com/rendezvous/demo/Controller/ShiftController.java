package com.rendezvous.demo.Controller;

import com.rendezvous.demo.Mapper.ShiftMapper;
import com.rendezvous.demo.service.ShiftService;
import com.rendezvous.demo.domain.Dtos.CreateshiftRequestDto;
import com.rendezvous.demo.domain.Dtos.GetShiftsDetailsResponseDto;
import com.rendezvous.demo.domain.Dtos.GetShiftsForExaminerResponseDto;
import com.rendezvous.demo.domain.Dtos.GetShiftsForSectionResponseDto;
import com.rendezvous.demo.domain.Entities.Shift;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rendezvous.demo.Util;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/shifts")
@RequiredArgsConstructor
public class ShiftController {
        private final ShiftService shiftService;
        private final ShiftMapper shiftMapper;

        @PostMapping("sections/{sectionId}/{examinerId}")
        public ResponseEntity<Void> createShift(
                        @RequestBody @Valid CreateshiftRequestDto createshiftRequestDto,
                        @PathVariable UUID examinerId,
                        @PathVariable UUID sectionId) {
                shiftService.createShiftForExaminer(examinerId, sectionId, createshiftRequestDto);
                return new ResponseEntity<>(HttpStatus.CREATED);
        }

        @DeleteMapping("{shiftId}")
        public ResponseEntity<Void> deleteShift(
                        @PathVariable UUID shiftId) {
                shiftService.deleteShift(shiftId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        @GetMapping("examiners/{examinerId}")
        public ResponseEntity<Page<GetShiftsForExaminerResponseDto>> getShiftsForExaminer(
                        @PathVariable UUID examinerId,
                        Pageable pageable) {
                Page<Shift> shifts = shiftService.getShiftsForExaminer(examinerId, pageable);
                return ResponseEntity.ok(
                                shifts.map(shiftMapper::toGetShiftsForExaminerResponseDto));
        }

        @GetMapping("sections/{sectionId}")
        public ResponseEntity<Page<GetShiftsForSectionResponseDto>> getShiftsForSection(
                        @PathVariable UUID sectionId,
                        Pageable pageable) {
                Page<Shift> shifts = shiftService.getShiftsForSection(sectionId, pageable);
                return ResponseEntity.ok(
                                shifts.map(shiftMapper::toGetShiftsForSectionResponseDto));
        }

        @GetMapping("{shiftId}")
        public ResponseEntity<GetShiftsDetailsResponseDto> getShiftById(
                        @PathVariable UUID shiftId) {
                Shift shift = shiftService.getShiftById(shiftId);
                return ResponseEntity.ok(
                                shiftMapper.toGetShiftsDetailsResponseDto(shift));
        }

        @PostMapping("cancel/{shiftId}")
        public ResponseEntity<Void> cancelShift(
                        @PathVariable UUID shiftId) {
                shiftService.cancelShift(shiftId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        @PostMapping("complete/{shiftId}")
        public ResponseEntity<Void> completeShift(
                        @PathVariable UUID shiftId) {
                shiftService.completeShift(shiftId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        @GetMapping("upcoming")
        public ResponseEntity<Page<GetShiftsForExaminerResponseDto>> getMyUpcomingShifts(
                        @AuthenticationPrincipal Jwt jwt,
                        Pageable pageable) {
                UUID examinerId = Util.parseUserId(jwt);
                Page<Shift> shifts = shiftService.getUpcomingShiftsForExaminer(examinerId, pageable);
                return ResponseEntity.ok(shifts.map(shiftMapper::toGetShiftsForExaminerResponseDto));
        }

        @GetMapping("upcoming/next")
        public ResponseEntity<GetShiftsDetailsResponseDto> getMyNextUpcomingShift(
                        @AuthenticationPrincipal Jwt jwt) {
                UUID examinerId = Util.parseUserId(jwt);
                Shift shift = shiftService.getNextUpcomingShiftForExaminer(examinerId);
                return ResponseEntity.ok(shiftMapper.toGetShiftsDetailsResponseDto(shift));
        }
}
