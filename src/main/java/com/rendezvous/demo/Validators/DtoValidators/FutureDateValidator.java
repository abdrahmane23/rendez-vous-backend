package com.rendezvous.demo.Validators.DtoValidators;


import com.rendezvous.demo.Validators.Annotations.FutureDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class FutureDateValidator implements ConstraintValidator<FutureDate, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime date, ConstraintValidatorContext context) {
        if (date == null) {
            return true;
        }
        return date.isAfter(LocalDateTime.now());
    }
}
