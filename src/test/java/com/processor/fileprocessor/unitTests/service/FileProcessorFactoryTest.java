package com.processor.fileprocessor.unitTests.service;

import com.processor.fileprocessor.constants.FileType;
import com.processor.fileprocessor.service.FileProcessorFactory;
import com.processor.fileprocessor.transactionHelperServices.TransactionBlanceCheckerService;
import com.processor.fileprocessor.transactionHelperServices.TransactionValidatorService;
import com.processor.fileprocessor.transactionProcessors.CSVProcessorService;
import com.processor.fileprocessor.transactionProcessors.TransactionProcessor;
import com.processor.fileprocessor.transactionProcessors.XMLProcessorService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

@RunWith(SpringRunner.class)
public class FileProcessorFactoryTest {
    @TestConfiguration
    static class FileProcessorFactoryTestContextConfiguration {
        @Bean
        public FileProcessorFactory fileProcessorFactory(){
            return new FileProcessorFactory();
        }
    }

    @Autowired
    private FileProcessorFactory factory;

    @MockBean
    private ApplicationContext context;

    @MockBean
    private CSVProcessorService csvService;

    @MockBean
    private XMLProcessorService xmlService;

    @MockBean
    private TransactionValidatorService validatorService;

    @MockBean
    private TransactionBlanceCheckerService blanceCheckerService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void createCSVFileProcessorTest(){
        TransactionProcessor csvProcessor = factory.createFileProcessor(FileType.CSV);
        Assert.assertNotNull(csvProcessor);
        Assert.assertTrue(csvProcessor instanceof CSVProcessorService);
    }

    @Test
    public void createXMLFileProcessorTest(){
        TransactionProcessor xmlProcessor = factory.createFileProcessor(FileType.XML);
        Assert.assertNotNull(xmlProcessor);
        Assert.assertTrue(xmlProcessor instanceof XMLProcessorService);
    }

    @Test
    public void testThatTryingToCreateOtherFileThrowsException(){
        exceptionRule.expect(ResponseStatusException.class);
        exceptionRule.expectMessage("Unsupported File Type!");
        factory.createFileProcessor(FileType.EXCEL);
    }
}
