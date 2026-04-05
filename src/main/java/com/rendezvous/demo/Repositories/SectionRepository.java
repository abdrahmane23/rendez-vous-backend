package com.rendezvous.demo.Repositories;

import com.rendezvous.demo.domain.Entities.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SectionRepository extends JpaRepository<Section,UUID> {

    
}
