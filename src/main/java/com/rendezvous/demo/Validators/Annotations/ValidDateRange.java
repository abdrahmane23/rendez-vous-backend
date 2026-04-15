package com.rendezvous.demo.Validators.Annotations;

import com.rendezvous.demo.Validators.Validator.DateRangeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateRangeValidator.class)
public @interface ValidDateRange {
    String message() default "start must be before end in at least 2 hours range";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
