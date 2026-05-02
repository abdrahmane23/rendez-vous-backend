package com.rendezvous.demo.service.Impl;

import com.rendezvous.demo.Exception.UserNotFoundException;
import com.rendezvous.demo.Mapper.UserMapper;
import com.rendezvous.demo.Repositories.UserRepo;
import com.rendezvous.demo.service.ExaminerService;
import com.rendezvous.demo.service.KeycloakAdminService;
import com.rendezvous.demo.Specification.UserSpecifications;
import com.rendezvous.demo.domain.Dtos.CreateExaminerRequestDto;
import com.rendezvous.demo.domain.Entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExaminerServiceImpl  implements ExaminerService {
    private final KeycloakAdminService keycloakAdminService;
    private final UserRepo userRepo;
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

    @Override
    public Page<User> searchExaminers(String query,String field, Pageable pageable) {
        Specification<User> spec = Specification.where(UserSpecifications.nameContains(query))
                .or(UserSpecifications.FirstnameContains(query))
                .and(UserSpecifications.fieldEquals(field));
        return userRepo.findAll(spec, pageable);
    }

}
