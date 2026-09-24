package com.rendezvous.demo.Service;

import com.rendezvous.demo.domain.Dtos.CreateshiftRequestDto;
import com.rendezvous.demo.domain.Entities.Shift;
import com.rendezvous.demo.domain.Entities.User;
import com.rendezvous.demo.domain.Enums.ShiftStatus;
import jakarta.validation.constraints.DecimalMax;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ShiftService {
    void createShiftForExaminer(UUID examinerId,UUID sectionId ,CreateshiftRequestDto shiftRequestDto);

    void deleteShift(UUID shiftId);

    Page<Shift> getShiftsForExaminer(UUID examinerId,Pageable pageable);

    Page<Shift> getShiftsForSection(UUID sectionId,Pageable pageable);
    List<Shift> getAllShiftsForSection(UUID sectionId);

    Shift getShiftById(UUID shiftId);
    List<User> getAvailableExaminers(UUID sectionId, LocalDateTime startTime, LocalDateTime endTime);
    User getExaminerIfAvailable(UUID sectionId,UUID examinerId, LocalDateTime startTime, LocalDateTime endTime);
    List <Shift> getShiftsForsectionAndExaminer(UUID sectionId, UUID examinerId, ShiftStatus shiftStatus,ShiftStatus shiftStatus1);
    void cancelShift(UUID shiftId);
    void completeShift(UUID shiftId);

    Page<Shift> getUpcomingShiftsForExaminer(UUID examinerId, Pageable pageable);
    Shift getNextUpcomingShiftForExaminer(UUID examinerId);


    void markShift(UUID shiftId);
}
