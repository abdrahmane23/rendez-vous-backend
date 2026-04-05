package com.rendezvous.demo.Repositories;


import com.rendezvous.demo.domain.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, UUID> {
    Page<User> findAll(Pageable pageable);
}
