package com.paytel.task.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paytel.task.model.dto.LogDataDto;
import com.paytel.task.model.dto.LogDataSearchResultDto;
import com.paytel.task.model.dto.NewLogDataDto;
import com.paytel.task.services.interfaces.LogDataSearchService;
import com.paytel.task.services.interfaces.LogDataService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LogDataControllerTest {

    private String URI = "/api/v1/logs";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LogDataService logDataService;

    @MockBean
    private LogDataSearchService logDataSearchService;

    private ObjectMapper mapper = new ObjectMapper();

    @ParameterizedTest
    @ValueSource(strings = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
    })
    void testAddNewLogShouldReturnContent(String content) throws Exception {
        //when
        when(logDataService.addNewLogEntry(any())).thenReturn(new LogDataDto());

        MvcResult result = mockMvc.perform(post(URI)
                .accept(content)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new NewLogDataDto())))
                .andExpect(status().isCreated())
                .andReturn();

        //then
        assertTrue(result.getResponse().getContentType() != null);
        assertTrue(result.getResponse().getContentType().equals(content));
    }

    @Test
    void getOne() throws Exception {
        //when
        when(logDataService.getOne(any())).thenReturn(new LogDataDto());

        mockMvc.perform(get(URI + "/{id}", "1"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
    })
    void testSearchLogsShouldReturnContent(String content) throws Exception {
        //given
        LogDataSearchResultDto logDataSearchResultDto = new LogDataSearchResultDto();
        logDataSearchResultDto.setLogs(new ArrayList<>());

        //when
        when(logDataSearchService.getLogsByCriteria(any())).thenReturn(logDataSearchResultDto);

        MvcResult result = mockMvc.perform(get(URI)
                .accept(content))
                .andExpect(status().isOk())
                .andReturn();

        //then
        assertTrue(result.getResponse().getContentType() != null);
        assertTrue(result.getResponse().getContentType().equals(content));
    }
}