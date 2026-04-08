package com.rendezvous.demo.domain.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTestRequestDto {

    UUID id;
    @NotBlank
    private String name;
    @NotNull
    private Duration duration;
    @NotNull
    private Duration timeOfResult;
}
