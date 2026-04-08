package com.rendezvous.demo.domain.Dtos;


import com.rendezvous.demo.domain.Entities.Test;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class GetSectionResponseDto {
    private UUID id;
    private String name;
    private String Location;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String testName;



}
