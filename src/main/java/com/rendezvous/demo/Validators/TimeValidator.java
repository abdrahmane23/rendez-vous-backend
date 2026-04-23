package com.rendezvous.demo.Validators;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class TimeValidator {

     public boolean ValidStartTime (LocalDateTime startTime, int duration) {
         return startTime.getMinute() % duration == 0
                 && startTime.getSecond() == 0
                 && startTime.getNano() == 0;
     }


}
