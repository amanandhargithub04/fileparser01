package com.processor.fileprocessor.transactionHelperServices;

import com.processor.fileprocessor.dto.Transaction;
import com.processor.fileprocessor.dto.Violation;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.processor.fileprocessor.constants.FileProcessorConstants.CREDIT;
import static com.processor.fileprocessor.constants.FileProcessorConstants.DEBIT;

@Service
public class TransactionValidatorService {

    /*
    * This method iterates over each and every imported transaction and validates them.
    * */
    public List<Violation> validateTransactionsAndReutrnViolationsIfAny(List<Transaction> transactionList) {
        List<Violation> violationList = new ArrayList<>();
        AtomicInteger counter = new AtomicInteger(0);

        transactionList.forEach(transaction -> {
            int order = counter.incrementAndGet();
            validateType(transaction.getType(), order, violationList);
            validateAmount(transaction.getAmount(), order, violationList);
            validateNarration(transaction.getNarration(), order, violationList);
        });

        return violationList;
    }


    private void validateType(String type, int order, List<Violation> violationList) {
        if (null == type || type.isEmpty()) {
            violationList.add(new Violation("Type", order, "The field is empty"));
        } else if (!CREDIT.equalsIgnoreCase(type) && !DEBIT.equalsIgnoreCase(type)) {
            violationList.add(new Violation("Type", order, "Should be 'C' for Credit OR 'D' for Debit but found : " + type));
        }
    }

    private void validateAmount(BigDecimal amount, int order, List<Violation> violationList) {
        if (null == amount || amount.compareTo(new BigDecimal(0)) <= 0) {
            violationList.add(new Violation("Amount", order, "The amount should be > 0"));
        }
    }

    private void validateNarration(String narration, int order, List<Violation> violationList) {
        if (null == narration || narration.isEmpty()) {
            violationList.add(new Violation(narration, order, "Narration is missing"));
        }
    }
}
