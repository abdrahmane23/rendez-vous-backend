package com.rendezvous.demo.Repositories;

import com.rendezvous.demo.domain.Entities.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ConsultationRepo extends JpaRepository<Consultation, UUID> {
}