package com.rendezvous.demo.domain.Dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class GetExaminerDetailsResponseDto {
    private UUID id;
    private String firstName;
    private String name;
    private String field;
    private String email;
    private String address;
    private String number;

}
