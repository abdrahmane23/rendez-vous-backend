package com.rendezvous.demo.domain.Dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateExaminerRequestDto {

    @NotBlank(message = "userName is required")
    private String userName;
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank (message = "Address is required")
    private String address;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Number is required")
    @Pattern(regexp = "^[0-9]+$", message = "Must contain only numbers")
    private String number;

    @NotBlank
    private String field;
}
