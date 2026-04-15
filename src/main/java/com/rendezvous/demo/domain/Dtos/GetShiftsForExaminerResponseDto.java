package com.rendezvous.demo.domain.Dtos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class GetShiftsForExaminerResponseDto {
    private UUID id;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String sectionName;

}
