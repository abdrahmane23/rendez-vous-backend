package com.rendezvous.demo.domain.Dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateExaminerRequestDto {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank (message = "Address is required")
    private String Address;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "^[0-9]+$", message = "Must contain only numbers")
    private String Number;

    @NotBlank
    private String field;
}
