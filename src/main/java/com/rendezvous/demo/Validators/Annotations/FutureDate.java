package com.rendezvous.demo.Validators.Annotations;

import com.rendezvous.demo.Validators.DtoValidators.FutureDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FutureDateValidator.class)
public @interface FutureDate {
    String message() default "start must be before end in at least 2 hours range";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
