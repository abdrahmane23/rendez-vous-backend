package com.rendezvous.demo.Scheduler;


import com.rendezvous.demo.Repositories.AppointementRepo;
import com.rendezvous.demo.Repositories.ShiftRepo;
import com.rendezvous.demo.Repositories.UserRepo;
import com.rendezvous.demo.Service.NotificationService;
import com.rendezvous.demo.domain.Dtos.NotificationDto;
import com.rendezvous.demo.domain.Dtos.TimeSlotResponseDto;
import com.rendezvous.demo.domain.Entities.Appointement;
import com.rendezvous.demo.domain.Entities.Shift;
import com.rendezvous.demo.domain.Entities.User;
import com.rendezvous.demo.domain.Enums.AppointementStatus;
import com.rendezvous.demo.domain.Enums.ShiftStatus;
import jakarta.transaction.Transactional;
import jdk.jfr.Registered;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DialectOverride;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {
    private final AppointementRepo appointementRepo;
    private final ShiftRepo shiftRepo;
    private final UserRepo userRepo;
    private final NotificationService notificationService;

    @Scheduled(fixedRate = 10000) // every 10 seconds
    public void runEvery10Seconds() {

        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setMessage( "testin notifications");
        notificationService.sendNotificationToAll(notificationDto);    }
    @Scheduled(fixedRate = 60 * 60 * 1000)
    @Transactional
    public void scanAppointmentTableForMarking(){
        Map<UUID,Integer> appointementMap = new HashMap<>();

        List< Appointement> appointements = appointementRepo.findByStatusAndStartLessThan(
                AppointementStatus.ACTIVE,
                LocalDateTime.now()
        );
        for (Appointement appointement : appointements) {
            UUID examinerId = appointement.getExaminer().getId();
            appointementMap.merge(examinerId, 1, Integer::sum);
        }

        for (Map.Entry<UUID, Integer> appointement : appointementMap.entrySet()) {
            int appointementCount = appointement.getValue();
            UUID examinerId = appointement.getKey();
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setMessage( "You have " + appointementCount + " active appointements that are past their start time. Please mark them as completed or non-attended.");
            notificationService.sendNotification(examinerId,notificationDto);
        }
    }
    @Scheduled(fixedRate = 60 * 60 * 1000)
    @Transactional
    public void scanAppointmentTableForResults(){
        Map<UUID,Integer> appointementMap = new HashMap<>();

        List< Appointement> appointements = appointementRepo.findByStatusAndStartLessThan(
                AppointementStatus.COMPLETED,
                LocalDateTime.now().minusHours(24)
        );
        for (Appointement appointement : appointements) {
            UUID examinerId = appointement.getExaminer().getId();
            appointementMap.merge(examinerId, 1, Integer::sum);
        }

        for (Map.Entry<UUID, Integer> appointement : appointementMap.entrySet()) {
            int appointementCount = appointement.getValue();
            UUID examinerId = appointement.getKey();
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setMessage( "You have " + appointementCount + " active appointements that are past the necessary for result. Please provide a result.");
            notificationService.sendNotification(examinerId,notificationDto);
        }
    }

    @Scheduled(fixedRate = 60 * 60 * 1000)
    @Transactional
    public void scanShiftTableForMarking(){
        int shiftCount = shiftRepo.countByStatusAndStartLessThan(
                ShiftStatus.ACTIVE,
                LocalDateTime.now()
        );
        List<User> admins = userRepo.findByRole("ADMIN");
        //since Shifts are not tied to Admin in the current design, we will just send a notification to all Admins if there are any active shifts that are past their start time
        for (User user: admins) {
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setMessage( "There are " + shiftCount + " active shifts that are past their start time. Please review them and mark them as completed or non-attended if necessary.");
            notificationService.sendNotification(user.getId(),notificationDto);
        }
    }

}
