package com.rendezvous.demo.domain.Dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class ExaminerForTimeSlotResponseDto {
    UUID id;
    String name;
}
