package com.processor.fileprocessor.controller;

import com.processor.fileprocessor.constants.FileType;
import com.processor.fileprocessor.dto.ProcessedDetails;
import com.processor.fileprocessor.service.FileProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
@RequestMapping("/processFile")
public class FileProcessorController {
    @Autowired
    private FileProcessorService fileProcessor;

    @RequestMapping(value = "/CSVFile", method = RequestMethod.POST)
    public ResponseEntity<ProcessedDetails> processCSVFile(@RequestParam("file") MultipartFile file) {
        try {
            return new ResponseEntity<>(fileProcessor.processAndReturnDetails(file, FileType.CSV), HttpStatus.OK);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error While Processing CSV File", e);
        }
    }

    @RequestMapping(value = "/XMLFile", method = RequestMethod.POST)
    public ResponseEntity<ProcessedDetails> processXMLFile(@RequestParam("file") MultipartFile file) {
        try {
            return new ResponseEntity<>(fileProcessor.processAndReturnDetails(file, FileType.XML), HttpStatus.OK);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error While Processing XML File", e);
        }
    }
}
