package com.processor.fileprocessor.unitTests.service;

import com.processor.fileprocessor.constants.FileType;
import com.processor.fileprocessor.dto.ProcessedDetails;
import com.processor.fileprocessor.dto.Transaction;
import com.processor.fileprocessor.dto.Violation;
import com.processor.fileprocessor.service.FileProcessorFactory;
import com.processor.fileprocessor.service.FileProcessorService;
import com.processor.fileprocessor.testHelper.FileProcessorTestHelper;
import com.processor.fileprocessor.transactionHelperServices.TransactionBlanceCheckerService;
import com.processor.fileprocessor.transactionHelperServices.TransactionValidatorService;
import com.processor.fileprocessor.transactionProcessors.CSVProcessorService;
import com.processor.fileprocessor.transactionProcessors.XMLProcessorService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.Csv;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static com.processor.fileprocessor.constants.FileProcessorConstants.TRANSACTIONS_NOT_BALANCED;
import static com.processor.fileprocessor.testHelper.ProcessorTestConstants.REQUEST_PARAM_NAME;

@RunWith(SpringRunner.class)
public class FileProcessorServiceTest {
    @TestConfiguration
    static class FileProcessorServiceTestContextConfiguration {
        @Bean
        public FileProcessorService fileProcessorService(){
            return new FileProcessorService();
        }
    }

    @Autowired
    private FileProcessorService fileProcessorService;

    @MockBean
    private FileProcessorFactory factory;

    @MockBean
    private CSVProcessorService csvProcessorService;

    @MockBean
    private XMLProcessorService xmlProcessorService;

    private static FileProcessorTestHelper helper;

    @BeforeClass
    public static void setUp(){
        helper = new FileProcessorTestHelper();
    }

    @Test
    public void processCSVFileAndReturnDetaisTest() throws IOException {
        List<Transaction> mockTxList = helper.mockTransactionListNotBalanced();
        List<Violation> mockViolationList = helper.mockViolationList();

        Mockito.when(factory.createFileProcessor(FileType.CSV)).thenReturn(csvProcessorService);
        Mockito.when(csvProcessorService.getImportedTransactions()).thenReturn(mockTxList);
        Mockito.when(csvProcessorService.validate()).thenReturn(mockViolationList);
        Mockito.when(csvProcessorService.isBalanced()).thenReturn(false);

        MockMultipartFile multipartFile = helper.getMockMultiPartFile(REQUEST_PARAM_NAME, "test_csv_not_balanced_with_violation.csv", "text/csv", "test_csv_not_balanced_with_violation.csv");
        ProcessedDetails actualProcessedDetails = fileProcessorService.processAndReturnDetails(multipartFile, FileType.CSV);

        Assert.assertNotNull(actualProcessedDetails);
        Assert.assertEquals(mockTxList, actualProcessedDetails.getImportedData());
        Assert.assertEquals(mockViolationList, actualProcessedDetails.getViolations());
        Assert.assertEquals(TRANSACTIONS_NOT_BALANCED, actualProcessedDetails.getTansactionStatus());
    }

    @Test
    public void processXMLFileAndReturnDetaisTest() throws IOException {
        List<Transaction> mockTxList = helper.mockTransactionListNotBalanced();
        List<Violation> mockViolationList = helper.mockViolationList();

        Mockito.when(factory.createFileProcessor(FileType.XML)).thenReturn(xmlProcessorService);
        Mockito.when(xmlProcessorService.getImportedTransactions()).thenReturn(mockTxList);
        Mockito.when(xmlProcessorService.validate()).thenReturn(mockViolationList);
        Mockito.when(xmlProcessorService.isBalanced()).thenReturn(false);

        MockMultipartFile multipartFile = helper.getMockMultiPartFile(REQUEST_PARAM_NAME, "test_xml_not_balanced_with_violation.xml", "text/xml","test_xml_not_balanced_with_violation.xml");
        ProcessedDetails actualProcessedDetails = fileProcessorService.processAndReturnDetails(multipartFile, FileType.XML);

        Assert.assertNotNull(actualProcessedDetails);
        Assert.assertEquals(mockTxList, actualProcessedDetails.getImportedData());
        Assert.assertEquals(mockViolationList, actualProcessedDetails.getViolations());
        Assert.assertEquals(TRANSACTIONS_NOT_BALANCED, actualProcessedDetails.getTansactionStatus());
    }
}
