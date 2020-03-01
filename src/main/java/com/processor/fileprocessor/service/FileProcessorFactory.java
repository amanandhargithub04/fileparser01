package com.processor.fileprocessor.service;

import com.processor.fileprocessor.constants.FileType;
import com.processor.fileprocessor.transactionProcessors.CSVProcessorService;
import com.processor.fileprocessor.transactionProcessors.TransactionProcessor;
import com.processor.fileprocessor.transactionProcessors.XMLProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class FileProcessorFactory {

    @Autowired
    private ApplicationContext context;

    public TransactionProcessor createFileProcessor(FileType type) {
        switch (type) {
            case CSV:
                return context.getBean(CSVProcessorService.class);
            case XML:
                return context.getBean(XMLProcessorService.class);
            default:
                throw new ResponseStatusException(
                        HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Unsupported File Type!");
        }
    }
}
