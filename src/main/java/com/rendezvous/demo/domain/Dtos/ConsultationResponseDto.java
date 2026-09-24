package com.rendezvous.demo.domain.Dtos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ConsultationResponseDto {
    private UUID id;
    private LocalDateTime createdAt;
    private String examinerName;
}
