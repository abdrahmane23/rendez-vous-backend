package com.rendezvous.demo.domain.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlotResponseDto {
    private LocalDateTime Start;
    private LocalDateTime End;
}

