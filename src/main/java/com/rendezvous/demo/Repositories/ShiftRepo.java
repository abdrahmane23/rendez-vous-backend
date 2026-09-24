package com.rendezvous.demo.Repositories;

import com.rendezvous.demo.domain.Entities.Shift;
import com.rendezvous.demo.domain.Enums.ShiftStatus;
import jakarta.persistence.LockModeType;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShiftRepo extends JpaRepository<Shift, UUID> {
    boolean existsByExaminerIdAndSectionIdAndStatusAndStartAndEnd(UUID examinerId, UUID sectionId, ShiftStatus status, LocalDateTime start, LocalDateTime end);

    Page<Shift> findByExaminerId(UUID examinerId,Pageable pageable);

    Page<Shift> findBySectionId(UUID sectionId, Pageable pageable);
    List<Shift> findBySectionIdAndStatusIn(UUID sectionId,Collection<ShiftStatus> statuses);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Shift> findBySectionIdAndStatusInAndStartLessThanEqualAndEndGreaterThanEqual(
            UUID sectionId,
            Collection<ShiftStatus> statuses,
            LocalDateTime providedEndDate,
            LocalDateTime providedStartDate
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Shift> findBySectionIdAndExaminerIdAndStatusInAndStartLessThanEqualAndEndGreaterThanEqual(
            UUID sectionId,
            UUID examinerId,
            Collection<ShiftStatus> statuses,
            LocalDateTime providedEndDate,
            LocalDateTime providedStartDate
    );

    int countBySectionIdAndStatusAndEnd(UUID sectionId, ShiftStatus shiftStatus, LocalDateTime localDateTime);

    int countBySectionIdAndStatusAndStart(UUID sectionId, ShiftStatus shiftStatus, LocalDateTime localDateTime);

    boolean existsByExaminerIdAndStatusAndStartLessThanAndEndGreaterThan(UUID examinerId, ShiftStatus shiftStatus,  LocalDateTime startTime,  LocalDateTime endTime);

    List<Shift> findBySectionIdAndExaminerIdAndStatusIn(UUID sectionId, UUID examinerId,Collection<ShiftStatus> statuses);

    int countBySectionIdAndStatusAndStartLessThanAndEndGreaterThan(UUID examinerId, ShiftStatus shiftStatus, @NotNull LocalDateTime endTime, @NotNull LocalDateTime startTime);

    Page<Shift> findByExaminerIdAndStatusInAndEndAfter(
            UUID examinerId,
            Collection<ShiftStatus> statuses,
            LocalDateTime end,
            Pageable pageable
    );


Optional<Shift> findFirstByExaminerIdAndStatusAndStartAfterOrderByStartAsc(
        UUID examinerId,
        ShiftStatus status,
        LocalDateTime now
);

@Query("""
        select s from Shift s
        where s.examiner.id = :examinerId
          and s.status in :statuses
          and s.start <= :appointmentStart
          and s.end >= :appointmentStart
        """)
Optional<Shift> findShiftForAppointmentValidation(
        @Param("examinerId") UUID examinerId,
        @Param("statuses") List<ShiftStatus> statuses,
        @Param("appointmentStart") LocalDateTime appointmentStart
);

    List<Shift> findByStatusAndStartLessThan(ShiftStatus shiftStatus, LocalDateTime now);

    int countByStatusAndStartLessThan(ShiftStatus shiftStatus, LocalDateTime now);


    List<Shift> findBySectionIdAndStatusAndEndAfter(UUID sectionId, ShiftStatus shiftStatus, LocalDateTime now);
}
