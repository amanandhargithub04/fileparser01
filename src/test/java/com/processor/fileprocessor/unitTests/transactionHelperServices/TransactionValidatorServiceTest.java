package com.processor.fileprocessor.unitTests.transactionHelperServices;

import com.processor.fileprocessor.testHelper.FileProcessorTestHelper;
import com.processor.fileprocessor.transactionHelperServices.TransactionValidatorService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TransactionValidatorServiceTest {
    @TestConfiguration
    static class TransactionValidatorServiceTestContextConfig {
        @Bean
        public TransactionValidatorService transactionValidatorService(){
            return new TransactionValidatorService();
        }
    }

    @Autowired
    private TransactionValidatorService validatorService;

    private static FileProcessorTestHelper helper;

    @BeforeClass
    public static void setUp(){
        helper = new FileProcessorTestHelper();
    }

    @Test
    public void verifyNoViolationsForBalancedTransactions() {
        Assert.assertTrue(validatorService.validateTransactionsAndReutrnViolationsIfAny(helper.mockTransactionListBalanced()).isEmpty());
    }

    @Test
    public void verifyViolationForNotBalancedTransactions() {
        Assert.assertEquals(1, validatorService.validateTransactionsAndReutrnViolationsIfAny(helper.mockTransactionListNotBalanced()).size());
    }

    @Test
    public void verifyViolationsValuesForNotBalancedTransactions() {
        String actualPropertyName = validatorService.validateTransactionsAndReutrnViolationsIfAny(helper.mockTransactionListNotBalanced()).get(0).getPropertyName();
        String expectedPropertyName = "Amount";
        Assert.assertEquals(expectedPropertyName, actualPropertyName);
    }

}
