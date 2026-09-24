package com.rendezvous.demo.domain.Dtos;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TestResultDetailsResponseDto {
    private UUID id;
    private String ReportUrl;
    private LocalDateTime createdAt;
    private String examinerName;
    private LocalDateTime appointementStart;

}
