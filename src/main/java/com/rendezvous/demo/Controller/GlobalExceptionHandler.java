package com.rendezvous.demo.Controller;

import com.rendezvous.demo.Exception.SectionNotFoundException;
import com.rendezvous.demo.Exception.UserNotCreatedException;
import com.rendezvous.demo.Exception.UserNotFoundException;
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
}
