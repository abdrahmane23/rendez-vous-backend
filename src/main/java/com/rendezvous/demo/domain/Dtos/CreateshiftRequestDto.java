package com.rendezvous.demo.domain.Dtos;

import com.rendezvous.demo.Validators.Annotations.FutureDate;
import com.rendezvous.demo.Validators.Annotations.ValidDateRange;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ValidDateRange
public class CreateshiftRequestDto {
    @NotNull
    @FutureDate
    private LocalDateTime startTime;
    @NotNull // end is implicitly futur without having a cusatom validator because of the @ValidDateRange that check duration wich needs to be a positive value
    private LocalDateTime endTime;
}
