package com.rendezvous.demo.Service.Impl;

import com.rendezvous.demo.Exception.UserNotFoundException;
import com.rendezvous.demo.Mapper.UserMapper;
import com.rendezvous.demo.Repositories.ShiftRepo;
import com.rendezvous.demo.Repositories.UserRepo;
import com.rendezvous.demo.Service.ExaminerService;
import com.rendezvous.demo.domain.Entities.Shift;
import com.rendezvous.demo.domain.Enums.ShiftStatus;
import com.rendezvous.demo.Service.KeycloakAdminService;
import com.rendezvous.demo.Specification.UserSpecifications;
import com.rendezvous.demo.domain.Dtos.CreateExaminerRequestDto;
import com.rendezvous.demo.domain.Entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ExaminerServiceImpl  implements ExaminerService {
    private final KeycloakAdminService keycloakAdminService;
    private final UserRepo userRepo;
    private final ShiftRepo shiftRepo;
    @Override
    public void addExaminer(CreateExaminerRequestDto createExaminerRequestDto) {
        keycloakAdminService.createUser( createExaminerRequestDto,"EXAMINER");
    }
    @Override
    public void addAdmin(CreateExaminerRequestDto createExaminerRequestDto) {
        keycloakAdminService.createUser( createExaminerRequestDto,"ADMIN");
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

    @Override
    public List<User> getExaminersBySectionId(UUID sectionId) {
        List<Shift> shifts = shiftRepo.findBySectionIdAndStatusAndEndAfter(
                sectionId,
                ShiftStatus.ACTIVE,
                LocalDateTime.now()
        );

        List<User> examiners = new ArrayList<>();
        Set<UUID> examinerIds = new HashSet<>();

        for (Shift shift : shifts) {
            User examiner = shift.getExaminer();

            if (examinerIds.add(examiner.getId())) {
                examiners.add(examiner);
            }
        }

        return examiners;
    }

}
