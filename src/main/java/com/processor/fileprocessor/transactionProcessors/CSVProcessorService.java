package com.processor.fileprocessor.transactionProcessors;

import com.processor.fileprocessor.transactionHelperServices.TransactionValidatorService;
import com.processor.fileprocessor.transactionHelperServices.TransactionBlanceCheckerService;
import com.processor.fileprocessor.dto.Transaction;
import com.processor.fileprocessor.dto.Violation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.processor.fileprocessor.constants.FileProcessorConstants.*;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CSVProcessorService implements TransactionProcessor {
    @Autowired
    private TransactionValidatorService validatorService;

    @Autowired
    private TransactionBlanceCheckerService checkerService;

    private List<Transaction> transactionList = new ArrayList<>();

    public void importTransactions(InputStream inputStream) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            this.transactionList = br.lines().skip(1).map(mapToTransactionList).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Unable to Parse CSV file", e);
        }
    }

    public List<Transaction> getImportedTransactions() {
        return this.transactionList;
    }

    public List<Violation> validate() {
        return validatorService.validateTransactionsAndReutrnViolationsIfAny(getImportedTransactions());
    }

    public Boolean isBalanced() {
        return checkerService.checkIfImportedTransactionsAreBalanced(getImportedTransactions());
    }


    private Function<String, Transaction> mapToTransactionList = (line) -> {
        Transaction transaction = new Transaction();
        String[] transactionRow = line.split(COMMA);
        populateType(transaction, transactionRow);
        populateAmount(transaction, transactionRow);
        populateNarration(transaction, transactionRow);

        return transaction;
    };

    private void populateNarration(Transaction transaction, String[] transactionRow) {
        if (!isTransactionRowNullOrEmpty(transactionRow, 2)) { //<--narration
            transaction.setNarration(transactionRow[2]);
        }
    }

    private void populateAmount(Transaction transaction, String[] transactionRow) {
        if (!isTransactionRowNullOrEmpty(transactionRow, 1)) { //<--amount
            transaction.setAmount(BigDecimal.valueOf(Double.valueOf(transactionRow[1])));
        }
    }

    private void populateType(Transaction transaction, String[] transactionRow) {
        if (!isTransactionRowNullOrEmpty(transactionRow, 0)) { //<--type
            transaction.setType(transactionRow[0]);//<-- this is Type
        }
    }

    private boolean isTransactionRowNullOrEmpty(String[] dataRow, int i) {
        return dataRow[i] == null || dataRow[i].isEmpty();
    }



}
