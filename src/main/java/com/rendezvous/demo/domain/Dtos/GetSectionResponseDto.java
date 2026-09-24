package com.rendezvous.demo.domain.Dtos;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class GetSectionResponseDto {
    private UUID id;
    private String name;
    private String Location;
    private int capacity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String testName;
    private Duration duration;



}
