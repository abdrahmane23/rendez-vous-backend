package com.rendezvous.demo.service;

import com.rendezvous.demo.domain.Dtos.CreateExaminerRequestDto;
import com.rendezvous.demo.domain.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ExaminerService {
    public void addExaminer(CreateExaminerRequestDto createExaminerRequestDto);
    void updateExaminer(UUID id, CreateExaminerRequestDto createExaminerRequestDto);
    Page<User> browseExaminers(Pageable pageable);

    User getExaminerById(UUID id);

    Page<User> searchExaminers(String query,String field , Pageable pageable);
}
