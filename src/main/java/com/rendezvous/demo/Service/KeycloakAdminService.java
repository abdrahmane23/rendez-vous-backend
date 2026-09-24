package com.rendezvous.demo.Service;

import com.rendezvous.demo.domain.Dtos.CreateExaminerRequestDto;

public interface KeycloakAdminService {
    public void createUser(CreateExaminerRequestDto createExaminerRequestDto,String role);
}
