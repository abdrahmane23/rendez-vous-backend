package com.rendezvous.demo.domain.Dtos;


import com.rendezvous.demo.domain.Enums.TestResultStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TestResultResponseDto {
    private UUID id;
    private LocalDateTime createdAt;
    private String examinerName;

}
