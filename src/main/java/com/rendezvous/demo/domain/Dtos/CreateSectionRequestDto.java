package com.rendezvous.demo.domain.Dtos;

import com.rendezvous.demo.domain.Entities.Test;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateSectionRequestDto {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Location is required")
    private String location;
    @Valid
    private CreateTestRequestDto test;

}
