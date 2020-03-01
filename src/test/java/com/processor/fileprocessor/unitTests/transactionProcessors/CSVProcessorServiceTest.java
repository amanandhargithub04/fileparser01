package com.processor.fileprocessor.unitTests.transactionProcessors;

import com.processor.fileprocessor.testHelper.FileProcessorTestHelper;
import com.processor.fileprocessor.transactionHelperServices.TransactionBlanceCheckerService;
import com.processor.fileprocessor.transactionHelperServices.TransactionValidatorService;
import com.processor.fileprocessor.transactionProcessors.CSVProcessorService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static com.processor.fileprocessor.testHelper.ProcessorTestConstants.REQUEST_PARAM_NAME;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class CSVProcessorServiceTest {
    @TestConfiguration
    static class CSVProcessorServiceTestContextConfiguration {
        @Bean
        public CSVProcessorService csvProcessorService(){
            return new CSVProcessorService();
        }
    }

    @Autowired
    private CSVProcessorService csvProcessorService;

    @MockBean
    private TransactionValidatorService validatorService;

    @MockBean
    private TransactionBlanceCheckerService balanceCheckerService;

    private static FileProcessorTestHelper helper;

    @BeforeClass
    public static void setUp(){
        helper = new FileProcessorTestHelper();
    }

    @Test
    public void verify_OnCallingImportTransactionsMehtod_NoInteractionWith_BalanceCheckerService_ValidatorService() throws IOException {
        MockMultipartFile mockMultipartFile = helper.getMockMultiPartFile(REQUEST_PARAM_NAME, "test_csv_not_balanced_with_violation.csv", "text/csv", "test_csv_not_balanced_with_violation.csv");
        csvProcessorService.importTransactions(mockMultipartFile.getInputStream());

        Assert.assertNotNull(csvProcessorService.getImportedTransactions());
        Mockito.verify(validatorService, never()).validateTransactionsAndReutrnViolationsIfAny(Mockito.anyList());
        Mockito.verify(balanceCheckerService, never()).checkIfImportedTransactionsAreBalanced(Mockito.anyList());
    }

    @Test
    public void verify_CallingValidateMethod_Interacts_OnceWithBalanceCheckerService_NoneWithValidatorService() throws IOException {
        MockMultipartFile mockMultipartFile = helper.getMockMultiPartFile(REQUEST_PARAM_NAME, "test_csv_not_balanced_with_violation.csv", "text/csv", "test_csv_not_balanced_with_violation.csv");
        csvProcessorService.importTransactions(mockMultipartFile.getInputStream());
        csvProcessorService.validate();

        Mockito.verify(validatorService, times(1)).validateTransactionsAndReutrnViolationsIfAny(Mockito.anyList());
        Mockito.verifyNoInteractions(balanceCheckerService);
    }

    @Test
    public void callingIsBalancedMethod_Interacts_OnceWithBalanceCheckerService_NoneWithValidatorService() throws IOException {
        MockMultipartFile mockMultipartFile = helper.getMockMultiPartFile(REQUEST_PARAM_NAME, "test_csv_not_balanced_with_violation.csv", "text/csv", "test_csv_not_balanced_with_violation.csv");
        csvProcessorService.importTransactions(mockMultipartFile.getInputStream());
        csvProcessorService.isBalanced();

        Mockito.verify(balanceCheckerService, times(1)).checkIfImportedTransactionsAreBalanced(Mockito.anyList());
        Mockito.verifyNoInteractions(validatorService);
    }

    @Test
    public void verifyServiceInteractionWithExactArgument() throws IOException {
        MockMultipartFile mockMultipartFile = helper.getMockMultiPartFile(REQUEST_PARAM_NAME, "test_csv_not_balanced_with_violation.csv", "text/csv", "test_csv_not_balanced_with_violation.csv");
        csvProcessorService.importTransactions(mockMultipartFile.getInputStream());
        csvProcessorService.isBalanced();
        csvProcessorService.validate();

        Mockito.verify(balanceCheckerService).checkIfImportedTransactionsAreBalanced(csvProcessorService.getImportedTransactions());
        Mockito.verify(validatorService).validateTransactionsAndReutrnViolationsIfAny(csvProcessorService.getImportedTransactions());
    }
}
