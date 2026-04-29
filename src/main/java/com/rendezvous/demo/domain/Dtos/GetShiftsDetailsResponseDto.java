package com.rendezvous.demo.domain.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetShiftsDetailsResponseDto {
    private UUID id;
    private LocalDateTime start;
    private LocalDateTime end;
    private GetExaminerForShiftResponseDto examiner;
    private GetSectionForShiftResponseDto section;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
