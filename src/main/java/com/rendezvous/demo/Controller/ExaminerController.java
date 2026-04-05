package com.rendezvous.demo.Controller;


import com.rendezvous.demo.Mapper.UserMapper;
import com.rendezvous.demo.Service.ExaminerService;
import com.rendezvous.demo.domain.Dtos.CreateExaminerRequestDto;
import com.rendezvous.demo.domain.Dtos.GetExaminerDetailsResponseDto;
import com.rendezvous.demo.domain.Dtos.GetExaminerResponseDto;
import com.rendezvous.demo.domain.Entities.User;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.UUID;

@RestController
@RequestMapping("/examiner")
public class ExaminerController {
    ExaminerService examinerService;
    UserMapper userMapper;
    @PostMapping
    public ResponseEntity<Void> createExaminer(
            @Valid CreateExaminerRequestDto ExaminerRequestDto
            ){
            examinerService.addExaminer(ExaminerRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<GetExaminerResponseDto>> browseExaminers(Pageable pageable){
        Page<User> examiners = examinerService.browseExaminers(pageable);

        return ResponseEntity.ok(
                examiners.map(userMapper::toDto));
    }
    @GetMapping("{id}")
    public ResponseEntity<GetExaminerDetailsResponseDto> getExaminerById(UUID id){
        User examiner = examinerService.getExaminerById(id);
        return ResponseEntity.ok(userMapper.toDetailsDto(examiner));
    }

    @PutMapping ("{id}")
    public ResponseEntity<Void> updateExaminer(@PathVariable UUID id, @Valid CreateExaminerRequestDto examinerRequestDto){
        examinerService.updateExaminer(id, examinerRequestDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
     }


}
