package com.processor.fileprocessor.unitTests.transactionHelperServices;

import com.processor.fileprocessor.testHelper.FileProcessorTestHelper;
import com.processor.fileprocessor.transactionHelperServices.TransactionBlanceCheckerService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
public class TransactionBalanceCheckerServiceTest {
    @TestConfiguration
    static class TransactionBalanceCheckerServiceTestContextConfig {
        @Bean
        public TransactionBlanceCheckerService transactionBlanceCheckerService(){
            return new TransactionBlanceCheckerService();
        }
    }

    @Autowired
    private TransactionBlanceCheckerService blanceCheckerService;

    private static FileProcessorTestHelper helper;

    @BeforeClass
    public static void setUp(){
        helper = new FileProcessorTestHelper();
    }

    @Test
    public void verifyForBalancedTransactions() {
        Assert.assertTrue(blanceCheckerService.checkIfImportedTransactionsAreBalanced(helper.mockTransactionListBalanced()));
    }

    @Test
    public void verifyForNotBalancedTransactions() {
        Assert.assertFalse(blanceCheckerService.checkIfImportedTransactionsAreBalanced(helper.mockTransactionListNotBalanced()));
    }
}
