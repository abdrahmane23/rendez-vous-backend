package com.rendezvous.demo.Service.Impl;

import com.rendezvous.demo.Exception.UserNotFoundException;
import com.rendezvous.demo.Mapper.UserMapper;
import com.rendezvous.demo.Repositories.UserRepo;
import com.rendezvous.demo.Service.ExaminerService;
import com.rendezvous.demo.Service.KeycloakAdminService;
import com.rendezvous.demo.domain.Dtos.CreateExaminerRequestDto;
import com.rendezvous.demo.domain.Entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExaminerServiceImpl  implements ExaminerService {
    UserMapper userMapper;
    KeycloakAdminService keycloakAdminService;
    UserRepo userRepo;
    @Override
    public void addExaminer(CreateExaminerRequestDto createExaminerRequestDto) {
        keycloakAdminService.createUser( createExaminerRequestDto);
    }

    @Override
    public void updateExaminer(UUID id, CreateExaminerRequestDto createExaminerRequestDto) {
        User existingExaminer = userRepo.findById(id).orElseThrow(
                ()-> new UserNotFoundException("Examiner not found")
        );
            existingExaminer.setFirstName(createExaminerRequestDto.getFirstName());
            existingExaminer.setName(createExaminerRequestDto.getName());
            existingExaminer.setEmail(createExaminerRequestDto.getEmail());
            existingExaminer.setNumber(createExaminerRequestDto.getNumber());
            existingExaminer.setAddress(createExaminerRequestDto.getAddress());
            existingExaminer.setField(createExaminerRequestDto.getField());
        userRepo.save(existingExaminer);
    }

    @Override
    public Page<User> browseExaminers(Pageable pageable) {
        return userRepo.findAll(pageable);
    }

    @Override
    public User getExaminerById(UUID id) {
        return userRepo.findById(id).orElseThrow(
                ()-> new UserNotFoundException("Examiner not found")
        );
    }
}
