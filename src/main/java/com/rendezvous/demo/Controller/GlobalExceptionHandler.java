package com.rendezvous.demo.Controller;

import com.rendezvous.demo.Exception.*;
import com.rendezvous.demo.domain.Dtos.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler{
    @ExceptionHandler(UserNotCreatedException.class)
    public ResponseEntity<ErrorDto> handleUserNotCreatedException(UserNotCreatedException ex){
        log.error("User creation failed: {}", ex.getMessage());
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("unable to create examiner");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundException(UserNotCreatedException ex){
        log.error("User not found: {}", ex.getMessage());
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("unable to find examiner");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(SectionNotFoundException.class)
    public ResponseEntity<ErrorDto> handleSectionNotFoundException(UserNotCreatedException ex){
        log.error("Section not found: {}", ex.getMessage());
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("unable to find section");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DuplicateShiftException.class)
    public ResponseEntity<ErrorDto> handleDuplicateShiftException(DuplicateShiftException ex){
        log.error("Duplicate shift: {}", ex.getMessage());
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("this shift already exist for this examiner and section");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ShiftTimingException.class)
    public ResponseEntity<ErrorDto> handleShiftTimingException(ShiftTimingException ex){
        log.error("Shift too far: {}", ex.getMessage());
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("unable to create shift, shift should be within 1 month from now");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ShiftNotFoundException.class)
    public ResponseEntity<ErrorDto> handleShiftNotFoundException(ShiftNotFoundException ex){
        log.error("Shift not found : {}", ex.getMessage());
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("unable to find shift");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(SectionFullException.class)
    public ResponseEntity<ErrorDto> handleSectionFullException(SectionFullException ex){
        log.error("Section is full : {}", ex.getMessage());
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("unable to create a shift because the section is full at that time ");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ShiftAlreadyCanceledException.class)
    public ResponseEntity<ErrorDto> handleShiftAlreadyCanceledException(ShiftAlreadyCanceledException ex){
        log.error("Shift is already cancelled : {}", ex.getMessage());
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("Shift is already cancelled");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ShiftAlreadyCompletedException.class)
    public ResponseEntity<ErrorDto> handleShiftAlreadyCompletedException(ShiftAlreadyCompletedException ex){
        log.error("Shift is already completed : {}", ex.getMessage());
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("Shift is already completed");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception ex) {
        log.error("Caught exception", ex);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("An unknown error occurred");
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
