package com.rendezvous.demo.Repositories;

import com.rendezvous.demo.domain.Entities.Shift;
import com.rendezvous.demo.domain.Enums.ShiftStatus;
import jakarta.persistence.LockModeType;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShiftRepo extends JpaRepository<Shift, UUID> {
    boolean existsByExaminerIdAndSectionIdAndStatusAndStartAndEnd(UUID examinerId, UUID sectionId, ShiftStatus status, LocalDateTime start, LocalDateTime end);

    Page<Shift> findByExaminerIdAndStatus(UUID examinerId,ShiftStatus status,Pageable pageable);

    Page<Shift> findBySectionIdAndStatus(UUID sectionId, ShiftStatus shiftStatus, Pageable pageable);
    List<Shift> findBySectionIdAndStatus(UUID sectionId, ShiftStatus shiftStatus);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Shift> findBySectionIdAndStatusAndStartLessThanEqualAndEndGreaterThanEqual(
            UUID sectionId,
            ShiftStatus status,
            LocalDateTime providedEndDate,
            LocalDateTime providedStartDate
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Shift> findBySectionIdAndExaminerIdAndStatusAndStartLessThanEqualAndEndGreaterThanEqual(
            UUID sectionId,
            UUID examinerId,
            ShiftStatus status,
            LocalDateTime providedEndDate,
            LocalDateTime providedStartDate
    );

    int countBySectionIdAndStatusAndEnd(UUID sectionId, ShiftStatus shiftStatus, LocalDateTime localDateTime);

    int countBySectionIdAndStatusAndStart(UUID sectionId, ShiftStatus shiftStatus, LocalDateTime localDateTime);

    boolean existsByExaminerIdAndStatusAndStartLessThanAndEndGreaterThan(UUID examinerId, ShiftStatus shiftStatus,  LocalDateTime startTime,  LocalDateTime endTime);

    List<Shift> findBySectionIdAndExaminerIdAndStatus(UUID sectionId, UUID examinerId, ShiftStatus shiftStatus);

    int countBySectionIdAndStatusAndStartLessThanAndEndGreaterThan(UUID examinerId, ShiftStatus shiftStatus, @NotNull LocalDateTime endTime, @NotNull LocalDateTime startTime);
}
