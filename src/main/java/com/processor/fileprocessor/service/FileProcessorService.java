package com.processor.fileprocessor.service;

import com.processor.fileprocessor.constants.FileProcessorConstants;
import com.processor.fileprocessor.constants.FileType;
import com.processor.fileprocessor.dto.ProcessedDetails;
import com.processor.fileprocessor.transactionProcessors.TransactionProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Service
public class FileProcessorService {
    @Autowired
    private FileProcessorFactory factory;

    public ProcessedDetails processAndReturnDetails(MultipartFile file, FileType fileType) {
        try {
            TransactionProcessor fileprocessor = this.factory.createFileProcessor(fileType);
            fileprocessor.importTransactions(file.getInputStream());
            return getProcessedDetails(fileprocessor);
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, fileType + " File To Process Not Found", e);
        }
    }

    private ProcessedDetails getProcessedDetails(TransactionProcessor fileprocessor) {
        ProcessedDetails processedDetails = new ProcessedDetails();
        processedDetails.setImportedData(fileprocessor.getImportedTransactions());
        processedDetails.setViolations(fileprocessor.validate());
        processedDetails.setTansactionStatus(fileprocessor.isBalanced() ? FileProcessorConstants.TRANSACTIONS_BALANCED : FileProcessorConstants.TRANSACTIONS_NOT_BALANCED);

        return processedDetails;
    }
}
