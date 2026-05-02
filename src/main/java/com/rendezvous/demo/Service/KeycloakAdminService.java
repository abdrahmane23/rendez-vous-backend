package com.rendezvous.demo.service;

import com.rendezvous.demo.domain.Dtos.CreateExaminerRequestDto;

public interface KeycloakAdminService {
    public void createUser(CreateExaminerRequestDto createExaminerRequestDto);
}
