package com.rendezvous.demo.Service.Impl;


import com.rendezvous.demo.Exception.*;
import com.rendezvous.demo.Repositories.SectionRepo;
import com.rendezvous.demo.Repositories.ShiftRepo;
import com.rendezvous.demo.Repositories.UserRepo;
import com.rendezvous.demo.Service.SectionService;
import com.rendezvous.demo.Service.ShiftService;
import com.rendezvous.demo.domain.Dtos.CreateshiftRequestDto;
import com.rendezvous.demo.domain.Entities.Section;
import com.rendezvous.demo.domain.Entities.Shift;
import com.rendezvous.demo.domain.Entities.User;
import com.rendezvous.demo.domain.Enums.ShiftStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShiftServiceImpl implements ShiftService {
    private final UserRepo userRepo;
    private final SectionRepo sectionRepo;
    private final ShiftRepo shiftRepo;


    @Override
    @Transactional
    public void createShiftForExaminer(UUID examinerId, UUID sectionId, CreateshiftRequestDto shiftRequestDto) {

        User examiner =userRepo.findById(examinerId).orElseThrow(() -> new UserNotFoundException("Examiner not found"));

        Section section =sectionRepo.findByIdWithLock(sectionId).orElseThrow(() -> new SectionNotFoundException("Section not found"));

        if (shiftRepo.existsByExaminerIdAndSectionIdAndStatusAndStartAndEnd(examinerId, sectionId, ShiftStatus.ACTIVE, shiftRequestDto.getStartTime(), shiftRequestDto.getEndTime())) {
            throw new DuplicateShiftException("An active shift already exists for this examiner in the specified section");
        }

        if (shiftRepo.existsByExaminerIdAndStatusAndStartLessThanAndEndGreaterThan(
                examinerId,
                ShiftStatus.ACTIVE,
                shiftRequestDto.getEndTime(),
                shiftRequestDto.getStartTime()
        )){
            throw new ShiftTimingException("the examiner already has shift in this time frame");
        }
        int PreviousShifts = shiftRepo.countBySectionIdAndStatusAndEnd(
                sectionId,
                ShiftStatus.ACTIVE,
                shiftRequestDto.getStartTime().minusHours(1)
        );
        int NextShifts = shiftRepo.countBySectionIdAndStatusAndStart(
                sectionId,
                ShiftStatus.ACTIVE,
                shiftRequestDto.getEndTime().plusHours(1)
        );

        if (PreviousShifts == section.getCapacity() || NextShifts == section.getCapacity()){
            throw new SectionFullException("Shift cant be created because is 1h away from the start of the shift");
        }

        int overlappingShifts = shiftRepo.countBySectionIdAndStatusAndStartLessThanAndEndGreaterThan(
                examinerId,
                ShiftStatus.ACTIVE,
                shiftRequestDto.getEndTime(),
                shiftRequestDto.getStartTime()
        );

        if (overlappingShifts == section.getCapacity()){
            throw new SectionFullException("Shift overlaps with existing shifts and section capacity is reached");
        }


        Shift newShift = new Shift();
        newShift.setExaminer(examiner);
        newShift.setSection(section);
        newShift.setStatus(ShiftStatus.ACTIVE);
        newShift.setStart(shiftRequestDto.getStartTime());
        newShift.setEnd(shiftRequestDto.getEndTime());
        shiftRepo.save(newShift);
    }

    @Override
    public void deleteShift(UUID shiftId) {
        Shift shift = shiftRepo.findById(shiftId).orElseThrow(
                () -> new ShiftNotFoundException("Shift not found"));
        shiftRepo.delete(shift);
    }

    @Override
    public Page<Shift> getShiftsForExaminer(UUID examinerId,Pageable pageable) {
            if (!userRepo.existsById(examinerId)) {
                throw new UserNotFoundException("Examiner not found");
            }
                return shiftRepo.findByExaminerIdAndStatus(examinerId, ShiftStatus.ACTIVE,pageable);
    }

    @Override
    public Page<Shift> getShiftsForSection(UUID sectionId,Pageable pageable) {
        if (!sectionRepo.existsById(sectionId)) {
            throw new SectionNotFoundException("Section not found");
        }
        return shiftRepo.findBySectionIdAndStatus(sectionId, ShiftStatus.ACTIVE,pageable);

    }

    @Override
    public Shift getShiftById(UUID shiftId) {
        return shiftRepo.findById(shiftId).orElseThrow(
                () -> new ShiftNotFoundException("Shift not found"));

    }

    @Override
    public Page<User> getAvailableExaminers(UUID sectionId, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable) {
        if (!sectionRepo.existsById(sectionId)) {
            throw new SectionNotFoundException("Section not found");
        }
        Page<Shift> shifts = shiftRepo.findBySectionIdAndStatusAndStartLessThanEqualAndEndGreaterThanEqual(
                sectionId,
                ShiftStatus.ACTIVE,
                startTime,
                endTime,
                pageable);
        Page<User> availableExaminers = shifts.map(Shift::getExaminer);
        return availableExaminers;
    }

    @Override
    public void cancelShift(UUID shiftId) {
        Shift shift = shiftRepo.findById(shiftId).orElseThrow(
                () -> new ShiftNotFoundException("Shift not found"));
        if (shift.getStatus()== ShiftStatus.CANCELED){
            throw new ShiftAlreadyCanceledException("Shift is already canceled");
        }
        if (shift.getStatus()== ShiftStatus.COMPLETED){
            throw new ShiftAlreadyCompletedException("Shift is already completed");
        }
        shift.setStatus(ShiftStatus.CANCELED);
    }

    @Override
    public void completeShift(UUID shiftId) {
        Shift shift = shiftRepo.findById(shiftId).orElseThrow(
                () -> new ShiftNotFoundException("Shift not found"));
        if (shift.getStatus()== ShiftStatus.CANCELED){
            throw new ShiftAlreadyCanceledException("Shift is already canceled");
        }
        if (shift.getStatus()== ShiftStatus.COMPLETED){
            throw new ShiftAlreadyCompletedException("Shift is already completed");
        }
        shift.setStatus(ShiftStatus.COMPLETED);
    }


}
