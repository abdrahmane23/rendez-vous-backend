package com.rendezvous.demo.domain.Dtos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class GetTestResultResponseDto {
    private UUID id;
    private String reportUrl;
    private LocalDateTime createdAt;
}