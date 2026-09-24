package com.rendezvous.demo.domain.Dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetSectionDetailsResponseDto {
    private UUID id;
    private String name;
    private String Location;
    private int capacity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private GetTestResponseDto test;



}
