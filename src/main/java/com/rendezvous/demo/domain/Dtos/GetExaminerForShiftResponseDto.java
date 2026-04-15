package com.rendezvous.demo.domain.Dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetExaminerForShiftResponseDto {
    private UUID id;
    private String name;
}
