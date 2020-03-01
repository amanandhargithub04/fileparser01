package com.processor.fileprocessor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @RequestMapping("/")
    public ResponseEntity<Object> indexMethod(){
        return new ResponseEntity<>("This is Default Request!!", HttpStatus.OK);
    }
}
