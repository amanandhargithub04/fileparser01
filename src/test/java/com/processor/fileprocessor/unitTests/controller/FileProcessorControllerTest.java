package com.processor.fileprocessor.unitTests.controller;

import com.processor.fileprocessor.constants.FileType;
import com.processor.fileprocessor.controller.FileProcessorController;
import com.processor.fileprocessor.service.FileProcessorService;
import com.processor.fileprocessor.testHelper.FileProcessorTestHelper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static com.processor.fileprocessor.testHelper.ProcessorTestConstants.REQUEST_PARAM_NAME;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FileProcessorController.class)
public class FileProcessorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileProcessorService service;

    private static FileProcessorTestHelper helper;

    @BeforeClass
    public static void setUp(){
        helper = new FileProcessorTestHelper();
    }

    @Test
    public void processCSVFileAndReturnProcessedDetailsTest() throws Exception {
        MockMultipartFile mockMultipartFile = helper.getMockMultiPartFile(REQUEST_PARAM_NAME, "test_csv_not_balanced_with_violation.csv", "text/csv", "test_csv_not_balanced_with_violation.csv");
        given(service.processAndReturnDetails(mockMultipartFile, FileType.CSV)).willReturn(helper.mockProcessedDetails_NotBalanced());

        mockMvc.perform(multipart("/processFile/CSVFile").file(mockMultipartFile))
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(helper.getExpectedJsonContentString_NotBalanced()))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void processXMLFileAndReturnProcessedDetailsTest() throws Exception {
        MockMultipartFile mockMultipartFile = helper.getMockMultiPartFile(REQUEST_PARAM_NAME, "test_xml_not_balanced_with_violation.xml", "text/xml","test_xml_not_balanced_with_violation.xml");
        given(service.processAndReturnDetails(mockMultipartFile, FileType.XML)).willReturn(helper.mockProcessedDetails_NotBalanced());

        mockMvc.perform(multipart("/processFile/XMLFile").file(mockMultipartFile))
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(helper.getExpectedJsonContentString_NotBalanced()))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }




}
