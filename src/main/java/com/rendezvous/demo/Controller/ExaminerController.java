package com.rendezvous.demo.Controller;


import com.rendezvous.demo.Mapper.UserMapper;
import com.rendezvous.demo.Service.ExaminerService;
import com.rendezvous.demo.Service.SectionService;
import com.rendezvous.demo.Service.ShiftService;
import com.rendezvous.demo.domain.Dtos.CreateExaminerRequestDto;
import com.rendezvous.demo.domain.Dtos.GetExaminerDetailsResponseDto;
import com.rendezvous.demo.domain.Dtos.GetExaminerResponseDto;
import com.rendezvous.demo.domain.Entities.Section;
import com.rendezvous.demo.domain.Entities.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/examiner")
@RequiredArgsConstructor
public class ExaminerController {
    private final ExaminerService examinerService;
    private final UserMapper userMapper;
    private final ShiftService shiftService;



    @GetMapping("/test")
    public String test(){
        return "test successful";
    }
    @PostMapping
    public ResponseEntity<Void> createExaminer(
             @Valid @RequestBody CreateExaminerRequestDto ExaminerRequestDto
            ){
            examinerService.addExaminer(ExaminerRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping ("/admin")
    public ResponseEntity<Void> createAdmin(
            @Valid @RequestBody CreateExaminerRequestDto ExaminerRequestDto
    ){
        examinerService.addAdmin(ExaminerRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<GetExaminerDetailsResponseDto> getExaminerById(@PathVariable UUID id){
        User examiner = examinerService.getExaminerById(id);
        return ResponseEntity.ok(userMapper.toDetailsDto(examiner));
    }


    @PutMapping ("{id}")
    public ResponseEntity<Void> updateExaminer(@PathVariable UUID id, @Valid @RequestBody CreateExaminerRequestDto examinerRequestDto){
        examinerService.updateExaminer(id, examinerRequestDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
     }

     @GetMapping
     public ResponseEntity<Page<GetExaminerResponseDto>> searchExaminers(@RequestParam(required = false) String query,
                                                                         @RequestParam(required = false) String field,
                                                                         Pageable pageable){
        Page<User> examiners = examinerService.searchExaminers(query,field, pageable);
        examiners.forEach(examiner -> System.out.println("examiner name: " + examiner.getFirstName() + " " + examiner.getRole()));
        Page<GetExaminerResponseDto> exam = examiners.map(userMapper::toDto);
        exam.forEach(examiner-> System.out.println("examiner dto name: " + examiner.getFirstName() + " " + examiner.getRole()));
        return ResponseEntity.ok(exam);
     }
     @GetMapping("/all/{sectionId}")
    public ResponseEntity<List<GetExaminerResponseDto>> getExaminersBySectionId(
            @PathVariable UUID sectionId){
         System.out.println("section id: " + sectionId);
        List<User> examiners = examinerService.getExaminersBySectionId(sectionId);
        return ResponseEntity.ok(examiners.stream().map(userMapper::toDto).toList());
     }
    @GetMapping("/available/{sectionId}")
    public ResponseEntity<List<GetExaminerResponseDto>> getAvaiableExaminersBySectionId(
            @PathVariable UUID sectionId,
            @RequestParam  LocalDateTime startTime,
            Pageable pageable){
        LocalDateTime endTime = startTime.plusMinutes(20);
        List<User> examiners = shiftService.getAvailableExaminers(sectionId,startTime,endTime);
        return ResponseEntity.ok(examiners.stream().map(userMapper::toDto).toList());
     }
}
