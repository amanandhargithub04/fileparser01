package com.processor.fileprocessor.transactionHelperServices;

import com.processor.fileprocessor.dto.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.processor.fileprocessor.constants.FileProcessorConstants.CREDIT;
import static com.processor.fileprocessor.constants.FileProcessorConstants.DEBIT;

@Service
public class TransactionBlanceCheckerService {

    public Boolean checkIfImportedTransactionsAreBalanced(List<Transaction> transactionList){
        BigDecimal debitSum = new BigDecimal(0);
        BigDecimal creditSum = new BigDecimal(0);
        for (Transaction transaction : transactionList) {
            if (transaction.getType().equalsIgnoreCase(DEBIT) && null != transaction.getAmount()) {
                debitSum = debitSum.add(transaction.getAmount());
            } else if (transaction.getType().equalsIgnoreCase(CREDIT) && null != transaction.getAmount()) {
                creditSum = creditSum.add(transaction.getAmount());
            }
        }

        return debitSum.equals(creditSum);
    }
}
