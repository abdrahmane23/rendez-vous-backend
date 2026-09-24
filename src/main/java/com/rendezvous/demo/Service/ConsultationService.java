package com.rendezvous.demo.Service;


import com.rendezvous.demo.domain.Entities.Consultation;

import java.util.List;
import java.util.UUID;

public interface ConsultationService {
    void cancelConsultation(UUID examinerId, UUID consultationId);

    List<Consultation> getConsultationForPatient(UUID patientId);

    Consultation getConsultationById(UUID consultationId);

    List<Consultation> getConsultationsForExaminer(UUID examinerId);
}
