package com.processor.fileprocessor.transactionProcessors;

import com.processor.fileprocessor.dto.Transaction;
import com.processor.fileprocessor.dto.Violation;

import java.io.InputStream;
import java.util.List;

public interface TransactionProcessor {

    public void importTransactions(InputStream inputStream);

    public List<Transaction> getImportedTransactions();

    public List<Violation> validate();

    public Boolean isBalanced();

}
