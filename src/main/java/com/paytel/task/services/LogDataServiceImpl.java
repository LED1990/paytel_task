package com.paytel.task.services;

import com.paytel.task.components.ExtractLogData;
import com.paytel.task.exceptions.LogNotFoundException;
import com.paytel.task.mappers.LogDataMapper;
import com.paytel.task.model.LogData;
import com.paytel.task.model.dto.LogDataDto;
import com.paytel.task.model.dto.NewLogDataDto;
import com.paytel.task.repository.LogDataRepository;
import com.paytel.task.services.interfaces.LogDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class LogDataServiceImpl implements LogDataService {

    private final ExtractLogData extractLogData;
    private final LogDataRepository logDataRepository;
    private LogDataMapper mapper = LogDataMapper.INSTANCE;

    @Autowired
    public LogDataServiceImpl(ExtractLogData extractLogData, LogDataRepository logDataRepository) {
        this.extractLogData = extractLogData;
        this.logDataRepository = logDataRepository;
    }

    @Override
    public LogDataDto addNewLogEntry(NewLogDataDto logDataDto) {
        if (logDataDto == null) {
            throw new IllegalArgumentException("Illegal argument, new log data cannot be null");
        }

        if (StringUtils.isEmpty(logDataDto.getLogMessage())) {
            throw new IllegalArgumentException("Illegal argument, no log message present");
        }

        Map<String, String> logsInfoMap = extractLogData.extractLogDataFromString(logDataDto.getLogMessage());
        Date date = extractLogData.convertDate(logsInfoMap);

        LogData newData = LogData.builder()
                .className(logsInfoMap.get(ExtractLogData.CLASS_NAME_KEY))
                .logDate(date)
                .logLevel(logsInfoMap.get(ExtractLogData.LOG_LEVEL_KEY))
                .logMessage(logsInfoMap.get(ExtractLogData.LOG_MESSAGE_KEY))
                .sourceAppName(logDataDto.getSourceAppName())
                .build();

        logDataRepository.save(newData);

        return mapper.logDataToDto(newData);
    }

    @Override
    public LogDataDto getOne(Long id) {
        return logDataRepository.findById(id).map(logData -> mapper.logDataToDto(logData)).orElseThrow(() -> new LogNotFoundException(id));
    }
}
