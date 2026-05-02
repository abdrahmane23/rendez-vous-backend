package com.rendezvous.demo.Service;


import java.util.UUID;

public interface ConsultationService {
    void cancelConsultation(UUID examinerId, UUID consultationId);
}
