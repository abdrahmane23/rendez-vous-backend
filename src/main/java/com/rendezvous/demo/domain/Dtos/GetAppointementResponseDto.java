package com.rendezvous.demo.domain.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAppointementResponseDto {
    private UUID id;
    private LocalDateTime start;
}
