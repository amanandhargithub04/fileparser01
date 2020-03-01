package com.processor.fileprocessor.testHelper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.processor.fileprocessor.dto.ProcessedDetails;
import com.processor.fileprocessor.dto.Transaction;
import com.processor.fileprocessor.dto.Violation;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.processor.fileprocessor.constants.FileProcessorConstants.*;

@Component
public class FileProcessorTestHelper {
    public FileProcessorTestHelper() {
    }

    private Transaction mockTransactionObject(String type, BigDecimal amount, String narration) {
        Transaction tx = new Transaction();
        tx.setType(type);
        tx.setAmount(amount);
        tx.setNarration(narration);
        return tx;
    }

    private Violation mockVioationObject(String property, int order, String errorMessage) {
        return new Violation(property, order, errorMessage);
    }

    private ProcessedDetails mockProcessedDetails(List transactions, List violations, String transactionStatus) {
        ProcessedDetails details = new ProcessedDetails();
        details.setImportedData(transactions);
        details.setViolations(violations);
        details.setTansactionStatus(transactionStatus);

        return details;
    }

    public List<Transaction> mockTransactionListNotBalanced() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(mockTransactionObject(DEBIT, new BigDecimal(61.22), "Electricity Bill"));
        transactions.add(mockTransactionObject(CREDIT, null, "Shopping Bill"));
        return transactions;
    }

    public List<Transaction> mockTransactionListBalanced() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(mockTransactionObject(CREDIT, new BigDecimal(12), "Allowance"));
        transactions.add(mockTransactionObject(DEBIT, new BigDecimal(12), "Shopping Bill"));
        return transactions;
    }

    public List<Violation> mockViolationList() {
        List<Violation> violations = new ArrayList<>();
        violations.add(mockVioationObject("Amount", 2, "The amount should be > 0"));
        return violations;
    }

    public ProcessedDetails mockProcessedDetails_NotBalanced(){
        return mockProcessedDetails(mockTransactionListNotBalanced(), mockViolationList(), TRANSACTIONS_NOT_BALANCED);
    }

    private ProcessedDetails mockProcessedDetails_Balanced(){
        return mockProcessedDetails(mockTransactionListBalanced(), new ArrayList(), TRANSACTIONS_BALANCED);
    }

    public String getExpectedJsonContentString_NotBalanced() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(mockProcessedDetails_NotBalanced());
    }

    public String getExpectedJsonContentString_Balanced() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(mockProcessedDetails_Balanced());
    }

    public MockMultipartFile getMockMultiPartFile(String reqParamName, String originalFileName, String contentType, String filePath) throws IOException {
        return new MockMultipartFile(reqParamName, originalFileName, contentType, new FileInputStream(new File(filePath)));
    }

}
