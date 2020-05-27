package com.paytel.task.mappers;

import com.paytel.task.model.LogData;
import com.paytel.task.model.dto.LogDataDto;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;


class LogDataMapperTest {

    private LogDataMapper mapper = LogDataMapper.INSTANCE;

    @Test
    void testLogDataToDto() {
        //given
        LogData logData = new LogData(1L,
                "source app",
                new Date(),
                "INFO",
                "some class",
                "test msg");
        //when
        LogDataDto logDataDto = mapper.logDataToDto(logData);

        //then

        assertTrue(logData.getLogMessage().equals(logDataDto.getLogMessage()));
        assertTrue(logData.getClassName().equals(logDataDto.getClassName()));
        assertTrue(logData.getLogLevel().equals(logDataDto.getLogLevel()));
        assertTrue(logData.getSourceAppName().equals(logDataDto.getSourceAppName()));
        assertTrue(logData.getLogDate().compareTo(logDataDto.getLogDate()) == 0);

    }

}