package com.processor.fileprocessor.testSuite;

import com.processor.fileprocessor.unitTests.controller.FileProcessorControllerTest;
import com.processor.fileprocessor.unitTests.service.FileProcessorFactoryTest;
import com.processor.fileprocessor.unitTests.service.FileProcessorServiceTest;
import com.processor.fileprocessor.unitTests.transactionHelperServices.TransactionValidatorServiceTest;
import com.processor.fileprocessor.unitTests.transactionProcessors.CSVProcessorServiceTest;
import com.processor.fileprocessor.unitTests.transactionProcessors.XMLProcessorServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({FileProcessorControllerTest.class,
        FileProcessorFactoryTest.class,
        FileProcessorServiceTest.class,
        TransactionValidatorServiceTest.class,
        TransactionValidatorServiceTest.class,
        CSVProcessorServiceTest.class,
        XMLProcessorServiceTest.class})
public class UnitTestSuite {
}
