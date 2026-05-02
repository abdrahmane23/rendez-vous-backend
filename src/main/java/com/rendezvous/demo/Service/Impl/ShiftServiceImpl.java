package com.rendezvous.demo.service.Impl;


import com.rendezvous.demo.Exception.*;
import com.rendezvous.demo.Repositories.SectionRepo;
import com.rendezvous.demo.Repositories.ShiftRepo;
import com.rendezvous.demo.Repositories.UserRepo;
import com.rendezvous.demo.service.SectionService;
import com.rendezvous.demo.service.ShiftService;
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
import java.util.List;
import java.util.Optional;
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
                sectionId,
                ShiftStatus.ACTIVE,
                shiftRequestDto.getEndTime(),
                shiftRequestDto.getStartTime()
        );
        System.out.println(overlappingShifts);

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
    public List<Shift> getAllShiftsForSection(UUID sectionId) {
        if (!sectionRepo.existsById(sectionId)) {
            throw new SectionNotFoundException("Section not found");
        }
        return shiftRepo.findBySectionIdAndStatus(sectionId, ShiftStatus.ACTIVE);

    }

    @Override
    public Shift getShiftById(UUID shiftId) {
        return shiftRepo.findById(shiftId).orElseThrow(
                () -> new ShiftNotFoundException("Shift not found"));

    }

    @Override
    public List<User> getAvailableExaminers(UUID sectionId, LocalDateTime startTime, LocalDateTime endTime) {
        if (!sectionRepo.existsById(sectionId)) {
            throw new SectionNotFoundException("Section not found");
        }
        System.out.println(sectionId);
        System.out.println(startTime);
        System.out.println(endTime);
        List<Shift> shifts = shiftRepo.findBySectionIdAndStatusAndStartLessThanEqualAndEndGreaterThanEqual(
                sectionId,
                ShiftStatus.ACTIVE,
                startTime,
                endTime);
        System.out.println(shifts.size());
        List<User> availableExaminers = shifts.stream().map(Shift::getExaminer).toList();
        return availableExaminers;
    }
    public User getExaminerIfAvailable(UUID sectionId ,UUID examinerId, LocalDateTime startTime, LocalDateTime endTime) {
        if (!sectionRepo.existsById(sectionId)) {
            throw new SectionNotFoundException("Section not found");
        }
        Optional<Shift> shift = shiftRepo.findBySectionIdAndExaminerIdAndStatusAndStartLessThanEqualAndEndGreaterThanEqual(
                sectionId,
                examinerId,
                ShiftStatus.ACTIVE,
                startTime,
                endTime
                );

        if (shift.isPresent()){
            return shift.get().getExaminer();
        }else {
            throw new ExaminerNotAvailableException("examiner doesnt have a shift at this time");
        }
    }

    @Override
    public List<Shift> getShiftsForsectionAndExaminer(UUID sectionId, UUID examinerId, ShiftStatus shiftStatus) {
        return shiftRepo.findBySectionIdAndExaminerIdAndStatus(
                sectionId,
                examinerId,
                shiftStatus
        );
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

    @Override
public Page<Shift> getUpcomingShiftsForExaminer(UUID examinerId, Pageable pageable) {
    if (!userRepo.existsById(examinerId)) {
        throw new UserNotFoundException("Examiner not found");
    }

    Pageable sortedPageable = org.springframework.data.domain.PageRequest.of(
            pageable.getPageNumber(),
            pageable.getPageSize(),
            org.springframework.data.domain.Sort.by("start").ascending()
    );

    return shiftRepo.findByExaminerIdAndStatusAndStartAfter(
            examinerId,
            ShiftStatus.ACTIVE,
            LocalDateTime.now(),
            sortedPageable
    );
}

@Override
public Shift getNextUpcomingShiftForExaminer(UUID examinerId) {
    if (!userRepo.existsById(examinerId)) {
        throw new UserNotFoundException("Examiner not found");
    }

    return shiftRepo.findFirstByExaminerIdAndStatusAndStartAfterOrderByStartAsc(
            examinerId,
            ShiftStatus.ACTIVE,
            LocalDateTime.now()
    ).orElseThrow(() -> new ShiftNotFoundException("No upcoming shift found"));
}


}
