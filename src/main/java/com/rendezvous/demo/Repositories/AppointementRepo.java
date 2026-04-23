package com.rendezvous.demo.Repositories;

import com.rendezvous.demo.domain.Entities.Appointement;
import com.rendezvous.demo.domain.Entities.User;
import com.rendezvous.demo.domain.Enums.AppointementStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AppointementRepo extends JpaRepository<Appointement, UUID> {


    boolean existsByExaminerAndStart(User examiner, LocalDateTime startTime);
    int countByPatientIdAndStatus(UUID patientId, AppointementStatus status);

    List<Appointement> findByExaminerIdAndStatusAndStartBetween(UUID examinerId, AppointementStatus appointementStatus, LocalDateTime start, LocalDateTime lastStart);

    Page<Appointement> findByPatientIdAndStatus(UUID patientId, AppointementStatus appointementStatus, Pageable pageable);


    boolean existsByPatientIdAndStartAndStatus(UUID patientId, LocalDateTime startTime, AppointementStatus appointementStatus);
}
