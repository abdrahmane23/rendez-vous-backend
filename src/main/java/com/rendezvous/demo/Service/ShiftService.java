package com.rendezvous.demo.Service;

import com.rendezvous.demo.domain.Dtos.CreateshiftRequestDto;
import com.rendezvous.demo.domain.Entities.Shift;
import com.rendezvous.demo.domain.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ShiftService {
    void createShiftForExaminer(UUID examinerId,UUID sectionId ,CreateshiftRequestDto shiftRequestDto);

    void deleteShift(UUID shiftId);

    Page<Shift> getShiftsForExaminer(UUID examinerId,Pageable pageable);

    Page<Shift> getShiftsForSection(UUID sectionId,Pageable pageable);

    Shift getShiftById(UUID shiftId);
    Page<User> getAvailableExaminers(UUID sectionId, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);
    void cancelShift(UUID shiftId);
    void completeShift(UUID shiftId);

}
