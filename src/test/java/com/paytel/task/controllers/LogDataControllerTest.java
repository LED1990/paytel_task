package com.paytel.task.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paytel.task.model.dto.LogDataDto;
import com.paytel.task.model.dto.NewLogDataDto;
import com.paytel.task.services.interfaces.LogDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void testAddNewLogShouldReturnJson() throws Exception {
        //when
        when(logDataService.addNewLogEntry(any())).thenReturn(new LogDataDto());

        MvcResult result = mockMvc.perform(post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new NewLogDataDto())))
                .andExpect(status().isCreated())
                .andReturn();

        //then
        assertTrue(result.getResponse().getContentType() != null);
        assertTrue(result.getResponse().getContentType().equals(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    void testAddNewLogShouldReturnXml() throws Exception {
        //when
        when(logDataService.addNewLogEntry(any())).thenReturn(new LogDataDto());

        MvcResult result = mockMvc.perform(post(URI)
                .accept(MediaType.APPLICATION_XML)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new NewLogDataDto())))
                .andExpect(status().isCreated())
                .andReturn();
        //then
        assertTrue(result.getResponse().getContentType() != null);
        assertTrue(result.getResponse().getContentType().equals(MediaType.APPLICATION_XML_VALUE));
    }

    @Test
    void getOne() throws Exception {
        //when
        when(logDataService.getOne(any())).thenReturn(new LogDataDto());

        mockMvc.perform(get(URI + "/{id}", "1"))
                .andExpect(status().isOk())
                .andReturn();
    }

}