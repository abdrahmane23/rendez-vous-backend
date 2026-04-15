package com.rendezvous.demo.domain.Dtos;

import com.rendezvous.demo.domain.Entities.Test;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSectionRequestDto {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Location is required")
    private String location;

    @Min(value = 1, message = "Capacity must be greater than 0")
    private int capacity;

    @Valid
    private CreateTestRequestDto test;

}
