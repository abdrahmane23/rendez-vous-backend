package com.rendezvous.demo.domain.Dtos;

import com.rendezvous.demo.domain.Enums.ShiftStatus;
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
    private ShiftStatus status;
    private GetExaminerForShiftResponseDto examiner;
    private GetSectionForShiftResponseDto section;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
