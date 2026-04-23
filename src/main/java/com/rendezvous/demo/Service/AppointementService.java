package com.rendezvous.demo.Service;

import com.rendezvous.demo.domain.Dtos.TimeSlotResponseDto;
import com.rendezvous.demo.domain.Entities.Appointement;
import com.rendezvous.demo.domain.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface AppointementService {

    void createAppointement(UUID patientId, UUID sectionId, UUID examinerId, LocalDateTime startTime);

    void createAppointementForTest(UUID patientId, UUID sectionId, LocalDateTime startTime);

    List<User> getAvailableExaminersForAppointement(UUID sectionId, LocalDateTime startTime);

    Map<UUID, List<TimeSlotResponseDto>> getAvailableTimeSlotsForSection(UUID sectionId);
     List<TimeSlotResponseDto> getAvailableTimeSlotsForTestSection(UUID sectionId);

    List<TimeSlotResponseDto> getAvailableTimeSlotsForExaminerInSection(UUID sectionId, UUID examinerId);

    Page<Appointement> getUpcomingAppointementsForPatient(UUID patientId, Pageable pageable);

    void cancelAppointement(UUID patientId, UUID appointementId);
}
