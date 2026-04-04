package com.rendezvous.demo.Controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/examiner")
public class ExaminerController {

    public ResponseEntity<Void> createExaminer(

    ){


        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
