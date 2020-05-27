package com.paytel.task.services;

import com.paytel.task.components.ExtractLogData;
import com.paytel.task.exceptions.LogNotFoundException;
import com.paytel.task.model.LogData;
import com.paytel.task.model.dto.LogDataDto;
import com.paytel.task.model.dto.NewLogDataDto;
import com.paytel.task.repository.LogDataRepository;
import com.paytel.task.services.interfaces.LogDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogDataServiceImplTest {

    private LogDataService logDataService;
    private ExtractLogData extractLogData;

    @Mock
    private LogDataRepository logDataRepository;


    @BeforeEach
    void init() {
        extractLogData = new ExtractLogData();
        logDataService = new LogDataServiceImpl(extractLogData, logDataRepository);
    }

    @Test
    void testAddNewLogEntry() {
        //given
        String logMsg = "2020-05-28 12:58:30,833 [main] ERROR com.paytel.task.TaskApplication - error log";
        NewLogDataDto newLogDataDto = new NewLogDataDto("Simple app", logMsg);
        ArgumentCaptor<LogData> logDataArgumentCaptor = ArgumentCaptor.forClass(LogData.class);

        //when
        LogDataDto result = logDataService.addNewLogEntry(newLogDataDto);
        verify(logDataRepository, times(1)).save(logDataArgumentCaptor.capture());

        //then
        assertTrue(result != null);
        assertTrue(logDataArgumentCaptor.getAllValues() != null);
        assertFalse(logDataArgumentCaptor.getAllValues().isEmpty());

    }

    @Test
    void testAddNewLogEntryWhenNullShouldThrowException() {
        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> logDataService.addNewLogEntry(null));

        //then
        assertTrue(exception.getMessage().equals("Illegal argument, new log data cannot be null"));
    }

    @Test
    void testAddNewLogEntryWhenNullMsgShouldThrowException() {
        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> logDataService.addNewLogEntry(new NewLogDataDto()));

        //then
        assertTrue(exception.getMessage().equals("Illegal argument, no log message present"));
    }

    @Test
    void testGetOneShouldThrowException() {
        //given
        Long id = 1L;
        //when
        when(logDataRepository.findById(id)).thenReturn(Optional.empty());
        Exception exception = assertThrows(LogNotFoundException.class, () -> logDataService.getOne(id));

        //then
        assertTrue(exception.getMessage().equals("Log not found id: " + id));
    }


}