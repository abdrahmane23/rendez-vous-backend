package com.rendezvous.demo.domain.Dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetTestResponseDto {
    private UUID id;
    private String name;
    private String duration;
    private String TimeOfResult;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
