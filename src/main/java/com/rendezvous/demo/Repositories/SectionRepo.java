package com.rendezvous.demo.Repositories;

import com.rendezvous.demo.domain.Entities.Section;
import jakarta.persistence.Id;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface SectionRepo extends JpaRepository<Section,UUID> {

    Page<Section> findAll( Pageable pageable);
    @Query("SELECT tt FROM Section tt WHERE tt.id = :id")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Section> findByIdWithLock(@Param("id") UUID sectionId);
}
