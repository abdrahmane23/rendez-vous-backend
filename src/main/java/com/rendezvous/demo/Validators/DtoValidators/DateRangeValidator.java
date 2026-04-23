package com.rendezvous.demo.Validators.DtoValidators;

import com.rendezvous.demo.Exception.ShiftTimingException;
import com.rendezvous.demo.Validators.Annotations.ValidDateRange;
import com.rendezvous.demo.domain.Dtos.CreateshiftRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Duration;
import java.time.LocalDateTime;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, CreateshiftRequestDto> {

    @Override
    public boolean isValid(CreateshiftRequestDto createshiftRequestDto, ConstraintValidatorContext constraintValidatorContext) {
        if (createshiftRequestDto == null || createshiftRequestDto.getStartTime() == null || createshiftRequestDto.getEndTime() == null) {
            return true;
        }
        boolean startsAtExactHour= createshiftRequestDto.getStartTime().getMinute() == 0
                && createshiftRequestDto.getStartTime().getSecond() == 0
                && createshiftRequestDto.getStartTime().getNano() == 0;
        Duration shiftDuration = Duration.between(createshiftRequestDto.getStartTime(), createshiftRequestDto.getEndTime());
        boolean durationIsExactHours = shiftDuration.toMinutes() % 60 == 0;
        boolean durationBetweenTwoAndEightHours = shiftDuration.compareTo(Duration.ofHours(2)) >=0 && shiftDuration.compareTo(Duration.ofHours(8)) <= 0;

        if (createshiftRequestDto.getEndTime().isAfter(LocalDateTime.now().plusMonths(1))) {
            throw new ShiftTimingException("Shift end time cannot be more than 1 months in the future");
        }

        return durationBetweenTwoAndEightHours
                && startsAtExactHour
                && durationIsExactHours ;
    }
}
