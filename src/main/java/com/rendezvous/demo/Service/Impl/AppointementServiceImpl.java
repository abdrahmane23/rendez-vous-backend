package com.rendezvous.demo.Service.Impl;

import com.rendezvous.demo.Exception.*;
import com.rendezvous.demo.Repositories.AppointementRepo;
import com.rendezvous.demo.Repositories.ConsultationRepo;
import com.rendezvous.demo.Repositories.SectionRepo;
import com.rendezvous.demo.Repositories.ShiftRepo;
import com.rendezvous.demo.Repositories.TestResultRepo;
import com.rendezvous.demo.Repositories.UserRepo;
import com.rendezvous.demo.Service.AppointementService;
import com.rendezvous.demo.Service.ShiftService;
import com.rendezvous.demo.Validators.TimeValidator;
import com.rendezvous.demo.domain.Dtos.TimeSlotResponseDto;
import com.rendezvous.demo.domain.Entities.Appointement;
import com.rendezvous.demo.domain.Entities.Section;
import com.rendezvous.demo.domain.Entities.Shift;
import com.rendezvous.demo.domain.Entities.User;
import com.rendezvous.demo.domain.Enums.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.rendezvous.demo.domain.Dtos.CreateConsultationRequestDto;
import com.rendezvous.demo.domain.Dtos.CreateTestResultRequestDto;
import com.rendezvous.demo.domain.Entities.Consultation;
import com.rendezvous.demo.domain.Entities.TestResult;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AppointementServiceImpl implements AppointementService {
    private final ShiftService shiftService;
    private final AppointementRepo appointementRepo;
    private final UserRepo userRepo;
    private final int CONSULTATION_DURATION = 20;
    private final TimeValidator timeValidator;
    private final SectionRepo sectionRepo;
    private final ShiftRepo shiftRepo;
    private final TestResultRepo testResultRepo;
    private final ConsultationRepo consultationRepo;

    private List<TimeSlotResponseDto> getAvailableTimeSlotsForExaminer(List<Shift> shifts, int duration) {
        List<TimeSlotResponseDto> possibleTimeSlots = GetPossibleTimeSlotsForExaminerInSection(shifts, duration);
        List<TimeSlotResponseDto> reservedTimeSlots = GetReservedTimeSlotsForExaminerInSection(shifts, duration);
        List<TimeSlotResponseDto> availableTimeSlots = GetAvailableTimeSlotsForExaminerInSection(possibleTimeSlots,
                reservedTimeSlots);
        return availableTimeSlots;
    }

    @Override
    @Transactional
    public void createAppointement(UUID patientId, UUID sectionId, UUID examinerId, LocalDateTime startTime) {
        User patient = userRepo.findById(patientId).orElseThrow(
                () -> new UserNotFoundException("Patient not found"));
        if (appointementRepo.countByPatientIdAndStatusAndStartAfter(patientId, AppointementStatus.ACTIVE,LocalDateTime.now()) == 5) {
            throw new AppointementExceededException("Patient already has 5 active appointement ");
        }

        if (!timeValidator.ValidStartTime(startTime, CONSULTATION_DURATION)) {
            throw new AppointementTimingException(
                    "Invalid start time. Appointement must be scheduled in thr recomanded time slot of the section wanted.");
        }
        if (appointementRepo.existsByPatientIdAndStartAndStatus(
                patientId,
                startTime,
                AppointementStatus.ACTIVE)) {
            throw new RuntimeException("appoinetement already booked for you at this time");
        }


        User examiner = shiftService.getExaminerIfAvailable(sectionId, examinerId, startTime,
                startTime.plusMinutes(CONSULTATION_DURATION));

        boolean isBooked = appointementRepo.existsByExaminerAndStart(examiner, startTime);

        if (isBooked) {
            throw new ExaminerNotAvailableException("Examiner is already booked at this time");
        }

        Appointement appointement = new Appointement();
        appointement.setPatient(patient);
        appointement.setExaminer(examiner);
        appointement.setType(appointementTypeEnum.CONSULTATION);
        appointement.setStart(startTime);
        appointement.setStatus(AppointementStatus.ACTIVE);
        appointementRepo.save(appointement);
    }

    @Override
    @Transactional
    public void createAppointementForTest(UUID patientId, UUID sectionId, LocalDateTime startTime) {
        User patient = userRepo.findById(patientId).orElseThrow(
                () -> new UserNotFoundException("Patient not found"));
        if (appointementRepo.countByPatientIdAndStatusAndStartAfter(patientId, AppointementStatus.ACTIVE,LocalDateTime.now()) == 5) {
            throw new AppointementExceededException("Patient already has 5 active appointement ");
        }

        Section section = sectionRepo.findById(sectionId).orElseThrow(
                () -> new SectionNotFoundException("Section not found"));
        if (section.getTest() == null) {
            throw new RuntimeException("Section does not have an associated test");
        }

        int testDuration = (int) section.getTest().getDuration().toMinutes();
        if (!timeValidator.ValidStartTime(startTime, testDuration)) {
            throw new AppointementTimingException(
                    "Invalid start time. Appointement must be scheduled in thr recommand-ed time slot of the section wanted.");
        }
        if (appointementRepo.existsByPatientIdAndStartAndStatus(
                patientId,
                startTime,
                AppointementStatus.ACTIVE)) {
            throw new RuntimeException("appoinetement already booked for you at this time");
        }

        List<User> availableExaminers = shiftService.getAvailableExaminers(sectionId, startTime,
                startTime.plusMinutes(testDuration));
        if (availableExaminers.isEmpty()) {
            throw new ExaminerNotAvailableException("No examiner is available at this time");
        }

        for (User examiner : availableExaminers) {
            boolean isBooked = appointementRepo.existsByExaminerAndStart(examiner, startTime);
            if (!isBooked) {
                Appointement appointement = new Appointement();
                appointement.setPatient(patient);
                appointement.setExaminer(examiner);
                appointement.setType(appointementTypeEnum.TEST);
                appointement.setStart(startTime);
                appointement.setStatus(AppointementStatus.ACTIVE);
                appointementRepo.save(appointement);
                return;
            }

        }
        throw new ExaminerNotAvailableException("All examiners are already booked at this time");
    }

    @Override
    @Transactional
    public List<User> getAvailableExaminersForAppointement(UUID sectionId, LocalDateTime startTime) {

        if (!timeValidator.ValidStartTime(startTime, CONSULTATION_DURATION)) {
            throw new AppointementTimingException(
                    "Invalid start time. Appointement must be scheduled in thr recomanded time slot of the section wanted.");
        }

        return shiftService.getAvailableExaminers(sectionId, startTime, startTime.plusMinutes(CONSULTATION_DURATION));
    }

    @Override
    public Map<UUID, List<TimeSlotResponseDto>> getAvailableTimeSlotsForSection(UUID sectionId) {
        List<Shift> shifts = shiftService.getAllShiftsForSection(sectionId);

        Map<UUID, List<Shift>> shiftsByExaminer = new HashMap<>();

        for (Shift shift : shifts) {
            UUID examinerId = shift.getExaminer().getId();
            shiftsByExaminer
                    .computeIfAbsent(examinerId, k -> new ArrayList<>())
                    .add(shift);
        }

        Map<UUID, List<TimeSlotResponseDto>> availableTimeSlotsByExaminer = new HashMap<>();

        for (Map.Entry<UUID, List<Shift>> entry : shiftsByExaminer.entrySet()) {

            List<TimeSlotResponseDto> availableTimeSlots =
                    getAvailableTimeSlotsForExaminer(
                            entry.getValue(),
                            CONSULTATION_DURATION
                    ).stream()
                            .filter(slot -> slot.getStart().isAfter(LocalDateTime.now()))
                            .toList();

            availableTimeSlotsByExaminer.put(entry.getKey(), availableTimeSlots);
        }

        return availableTimeSlotsByExaminer;
    }

    @Override
    public List<TimeSlotResponseDto> getAvailableTimeSlotsForTestSection(UUID sectionId) {
        List<Shift> shifts = shiftService.getAllShiftsForSection(sectionId);
        int duration = (int) shifts.getFirst().getSection().getTest().getDuration().toMinutes();
        System.out.println(duration);
        Map<UUID, List<Shift>> shiftsByExaminer = new HashMap<>();
        for (Shift shift : shifts) {
            UUID examinerId = shift.getExaminer().getId();
            shiftsByExaminer.computeIfAbsent(examinerId, k -> new ArrayList<>()).add(shift);
        }
        Map<UUID, List<TimeSlotResponseDto>> availableTimeSlotsByExaminer = new HashMap<>();
        for (Map.Entry<UUID, List<Shift>> shift : shiftsByExaminer.entrySet()) {
            List<TimeSlotResponseDto> availableTimeSlots = getAvailableTimeSlotsForExaminer(shift.getValue(), duration);
            availableTimeSlotsByExaminer.put(shift.getKey(), availableTimeSlots);
        }
        List<TimeSlotResponseDto> availableTimeSlotsForTest =
                availableTimeSlotsByExaminer.values()
                        .stream()
                        .flatMap(List::stream)
                        .filter(slot -> slot.getStart().isAfter(LocalDateTime.now()))
                        .toList();
        return availableTimeSlotsForTest;
    }

    @Override
    public List<TimeSlotResponseDto> getAvailableTimeSlotsForExaminerInSection(UUID sectionId, UUID examinerId) {
        List<Shift> shifts = shiftService.getShiftsForsectionAndExaminer(sectionId, examinerId, ShiftStatus.ACTIVE,ShiftStatus.COMPLETED);
        List<TimeSlotResponseDto> availableTimeSlots = getAvailableTimeSlotsForExaminer(shifts, CONSULTATION_DURATION);
        return availableTimeSlots;
    }

    @Override
    public Page<Appointement> getUpcomingAppointementsForPatient(UUID patientId, Pageable pageable) {

        return appointementRepo.findByPatientIdAndStatusAndStartAfter(
                patientId,
                AppointementStatus.ACTIVE,
                LocalDateTime.now(),
                pageable);
    }

    @Override
    @Transactional
    public void cancelAppointement(UUID patientId, UUID appointementId) {
        System.out.println(appointementId);
        Appointement appointement = appointementRepo.findById(appointementId).orElseThrow(
                () -> new RuntimeException("there is no appointement with this id "));
        if (!appointement.getPatient().getId().equals(patientId)) {
            throw new RuntimeException("appointement doesn't belong to this patient");
        }
        if (!appointement.getStatus().equals(AppointementStatus.ACTIVE)) {
            throw new RuntimeException("the appointement is " + appointement.getStatus() + ",you cant cancel it");
        }
        Duration remainingTime = Duration.between(LocalDateTime.now(), appointement.getStart());

        if (remainingTime.compareTo(Duration.ofHours(24)) <= 0) {
            throw new RuntimeException("u cant cancel appointement because there still less then 24h for it ");
        }
        appointement.setStatus(AppointementStatus.CANCELED);
        System.out.println(appointement.getStatus());

    }

    private List<TimeSlotResponseDto> GetPossibleTimeSlotsForExaminerInSection(List<Shift> shifts, int duration) {
        List<TimeSlotResponseDto> availableTimeSlots = new ArrayList<>();
        for (Shift shift : shifts) {
            LocalDateTime startTime = shift.getStart();
            LocalDateTime endTime = startTime.plusMinutes(duration);
            while (endTime.isBefore(shift.getEnd()) || endTime.isEqual(shift.getEnd())) {
                TimeSlotResponseDto timeSlotResponseDto = new TimeSlotResponseDto();
                timeSlotResponseDto.setStart(startTime);
                timeSlotResponseDto.setEnd(endTime);
                availableTimeSlots.add(timeSlotResponseDto);
                startTime = endTime;
                endTime = startTime.plusMinutes(duration);
            }
        }
        return availableTimeSlots;
    }

    private List<TimeSlotResponseDto> GetReservedTimeSlotsForExaminerInSection(List<Shift> shifts, int duration) {
        List<Appointement> appointements = new ArrayList<>();
        for (Shift shift : shifts) {
            List<Appointement> appointementInShift = appointementRepo.findByExaminerIdAndStatusAndStartBetween(
                    shift.getExaminer().getId(),
                    AppointementStatus.ACTIVE,
                    shift.getStart(),
                    shift.getEnd().minusMinutes(duration));
            appointements.addAll(appointementInShift);
        }
        List<TimeSlotResponseDto> reservedTimeSlots = new ArrayList<>();
        for (Appointement appointement : appointements) {
            TimeSlotResponseDto timeSlotResponseDto = new TimeSlotResponseDto();
            timeSlotResponseDto.setStart(appointement.getStart());
            timeSlotResponseDto.setEnd(appointement.getStart().plusMinutes(duration));
            reservedTimeSlots.add(timeSlotResponseDto);
        }
        return reservedTimeSlots;
    }

    private List<TimeSlotResponseDto> GetAvailableTimeSlotsForExaminerInSection(
            List<TimeSlotResponseDto> possibleTimeSlots, List<TimeSlotResponseDto> reservedTimeSlots) {
        Set<TimeSlotResponseDto> reservedTimeSlotSet = new HashSet<>(reservedTimeSlots);
        List<TimeSlotResponseDto> availableTimeSlots = possibleTimeSlots.stream().filter(
                timeSlot -> !reservedTimeSlotSet.contains(timeSlot)).toList();
        return availableTimeSlots;
    }

    @Override
    public List<Appointement> getAppointementsForShift(UUID examinerId, UUID shiftId) {
        Shift shift = shiftRepo.findById(shiftId).orElseThrow(
                () -> new ShiftNotFoundException("Shift not found"));

        if (!shift.getExaminer().getId().equals(examinerId)) {
            throw new RuntimeException("This shift does not belong to this examiner");
        }

        return appointementRepo.findAppointementsInShiftSorted(
                examinerId,
                shift.getStart(),
                shift.getEnd());
    }

    @Override
    @Transactional
    public void markAppointementAsCompleted(UUID examinerId, UUID appointementId) {
        Appointement appointement = getValidAppointementForExaminerAction(examinerId, appointementId);
        appointement.setStatus(AppointementStatus.COMPLETED);
    }

    @Override
    @Transactional
    public void markAppointementAsNonAttended(UUID examinerId, UUID appointementId) {
        Appointement appointement = getValidAppointementForExaminerAction(examinerId, appointementId);
        appointement.setStatus(AppointementStatus.NON_ATTENDED);
    }

    @Override
    @Transactional
    public void createTestResult(UUID examinerId, UUID appointementId, CreateTestResultRequestDto requestDto) {
        Appointement appointement = getValidAppointementForResultOrConsultation(examinerId, appointementId);
        appointement.setStatus(AppointementStatus.FINALIZED);

        TestResult testResult = new TestResult();
        testResult.setAppointement(appointement);
        testResult.setReportUrl(requestDto.getReportUrl());
        testResult.setStatus(TestResultStatus.ACTIVE);

        testResultRepo.save(testResult);
    }

    @Override
    @Transactional
    public void createConsultation(UUID examinerId, UUID appointementId, CreateConsultationRequestDto requestDto) {
        Appointement appointement = getValidAppointementForResultOrConsultation(examinerId, appointementId);
        appointement.setStatus(AppointementStatus.FINALIZED);

        Consultation consultation = new Consultation();
        consultation.setAppointement(appointement);
        consultation.setReportUrl(requestDto.getReportUrl());
        consultation.setStatus(ConsultationStatus.ACTIVE);

        consultationRepo.save(consultation);
    }

    private Appointement getValidAppointementForExaminerAction(UUID examinerId, UUID appointementId) {
        Appointement appointement = appointementRepo.findById(appointementId).orElseThrow(
                () -> new RuntimeException("Appointement not found"));

        if (!appointement.getExaminer().getId().equals(examinerId)) {
            throw new RuntimeException("This appointement does not belong to this examiner");
        }

        if (LocalDateTime.now().isBefore(appointement.getStart())) {
            throw new AppointementTimingException("You can only update the appointement after its start time");
        }

        shiftRepo.findShiftForAppointmentValidation(
                examinerId,
                List.of(ShiftStatus.ACTIVE, ShiftStatus.COMPLETED),
                appointement.getStart()).orElseThrow(
                        () -> new ShiftNotFoundException("No active or completed shift found for this appointement"));

        return appointement;
    }

    private Appointement getValidAppointementForResultOrConsultation(UUID examinerId, UUID appointementId) {
        Appointement appointement = getValidAppointementForExaminerAction(examinerId, appointementId);

        if (!appointement.getStatus().equals(AppointementStatus.COMPLETED)) {
            throw new RuntimeException(
                    "Cannot create result or consultation for a non completed appointement");
        }

        return appointement;
    }

}