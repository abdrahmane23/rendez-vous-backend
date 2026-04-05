package com.rendezvous.demo.Service;

import com.rendezvous.demo.domain.Dtos.CreateExaminerRequestDto;
import com.rendezvous.demo.domain.Entities.User;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.UUID;

public interface ExaminerService {
    public void addExaminer(CreateExaminerRequestDto createExaminerRequestDto);
    void updateExaminer(UUID id, CreateExaminerRequestDto createExaminerRequestDto);
    Page<User> browseExaminers(Pageable pageable);

    User getExaminerById(UUID id);
}
