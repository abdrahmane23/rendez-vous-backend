package com.rendezvous.demo.Repositories;

import com.rendezvous.demo.domain.Entities.Section;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface SectionRepo extends JpaRepository<Section,UUID> {
    Page<Section> findAll( Pageable pageable);

    
}
