package com.rendezvous.demo.Service.Impl;

import com.rendezvous.demo.Exception.UserNotFoundException;
import com.rendezvous.demo.Repositories.ConsultationRepo;
import com.rendezvous.demo.Repositories.UserRepo;
import com.rendezvous.demo.Service.ConsultationService;
import com.rendezvous.demo.domain.Entities.Appointement;
import com.rendezvous.demo.domain.Entities.Consultation;
import com.rendezvous.demo.domain.Entities.User;
import com.rendezvous.demo.domain.Enums.AppointementStatus;
import com.rendezvous.demo.domain.Enums.ConsultationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class ConsultationServiceImpl implements ConsultationService {
    private final UserRepo userRepo;
    private final ConsultationRepo consultationRepo;
    @Override
    public void cancelConsultation(UUID examinerId, UUID consultationId) {
        User examiner= userRepo.findById(examinerId).orElseThrow(
                ()-> new UserNotFoundException("Examiner not found")
        );

        Consultation consultation = consultationRepo.findById(consultationId).orElseThrow(
                ()-> new RuntimeException("Consultation not found")
        );
        Appointement appointement = consultation.getAppointement();
        if (!appointement.getExaminer().equals(examiner)){
            throw new RuntimeException("You are not authorized to cancel this consultation");
        }
        if (consultation.getStatus().equals(ConsultationStatus.CANCELED)){
            throw new RuntimeException("This consultation is already canceled");
        };
        appointement.setStatus(AppointementStatus.COMPLETED);
        consultation.setStatus(ConsultationStatus.CANCELED);


    }

    @Override
    public List<Consultation> getConsultationForPatient(UUID patientId) {
        return consultationRepo.findByAppointementPatientId(patientId);
    }

    @Override
    public Consultation getConsultationById(UUID consultationId) {
        return consultationRepo.findById(consultationId).orElseThrow(
                ()-> new RuntimeException("Consultation not found")
        );
    }

    @Override
    public List<Consultation> getConsultationsForExaminer(UUID patientId) {
        return consultationRepo.findByAppointementPatientId(patientId);
    }
}
