package com.rendezvous.demo.Repositories;

import com.rendezvous.demo.domain.Entities.Shift;
import com.rendezvous.demo.domain.Enums.ShiftStatus;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface ShiftRepo extends JpaRepository<Shift, UUID> {
    boolean existsByExaminerIdAndSectionIdAndStatusAndStartAndEnd(UUID examinerId, UUID sectionId, ShiftStatus status, LocalDateTime start, LocalDateTime end);

    Page<Shift> findByExaminerIdAndStatus(UUID examinerId,ShiftStatus status,Pageable pageable);

    Page<Shift> findBySectionIdAndStatus(UUID sectionId, ShiftStatus shiftStatus,Pageable pageable);
    Page<Shift> findBySectionIdAndStatusAndStartLessThanEqualAndEndGreaterThanEqual(
            UUID sectionId,
            ShiftStatus status,
            LocalDateTime providedEndDate,
            LocalDateTime providedStartDate,
            Pageable pageable
    );

    int countBySectionIdAndStatusAndStartLessThanAndEndGreaterThan(UUID sectionId, ShiftStatus shiftStatus, LocalDateTime endTime, LocalDateTime startTime);

    boolean existsByExaminerIdAndStatusAndStartOrEnd(UUID examinerId, ShiftStatus shiftStatus,  LocalDateTime startTime, LocalDateTime endTime);

    int countBySectionIdAndStatusAndEnd(UUID sectionId, ShiftStatus shiftStatus, LocalDateTime localDateTime);

    int countBySectionIdAndStatusAndStart(UUID sectionId, ShiftStatus shiftStatus, LocalDateTime localDateTime);

    boolean existsByExaminerIdAndStatusAndStartLessThanAndEndGreaterThan(UUID examinerId, ShiftStatus shiftStatus,  LocalDateTime startTime,  LocalDateTime endTime);
}
