package com.rendezvous.demo.Repositories;

import com.rendezvous.demo.domain.Entities.Appointement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppointementRepo extends JpaRepository<Appointement, UUID> {


}
