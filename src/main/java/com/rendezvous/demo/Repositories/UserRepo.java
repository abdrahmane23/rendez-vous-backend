package com.rendezvous.demo.Repositories;


import com.rendezvous.demo.domain.Entities.User;
import com.rendezvous.demo.domain.Enums.ShiftStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    List<User> findByRole(String admin);
    List<User> findDistinctByShiftsSectionIdAndShiftsStatusAndShiftsEndAfter(
            UUID sectionId,
            ShiftStatus status,
            LocalDateTime now
    );
    Page<User> findAll(Pageable pageable);
    Page<User> findAll(Specification<User> spec, Pageable pageable);
}
