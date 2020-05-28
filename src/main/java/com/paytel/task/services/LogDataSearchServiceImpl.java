package com.paytel.task.services;

import com.paytel.task.exceptions.LogNotFoundException;
import com.paytel.task.mappers.LogDataMapper;
import com.paytel.task.model.LogsSearchCriteria;
import com.paytel.task.model.LogsSearchResult;
import com.paytel.task.model.dto.LogDataDto;
import com.paytel.task.model.dto.LogDataSearchResultDto;
import com.paytel.task.repository.LogDataRepository;
import com.paytel.task.services.interfaces.LogDataSearchService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LogDataSearchServiceImpl implements LogDataSearchService {

    private final LogDataRepository logDataRepository;

    private LogDataMapper mapper = LogDataMapper.INSTANCE;

    public LogDataSearchServiceImpl(LogDataRepository logDataRepository) {
        this.logDataRepository = logDataRepository;
    }

    @Override
    public LogDataSearchResultDto getLogsByCriteria(LogsSearchCriteria logsSearchCriteria) {
        if (logsSearchCriteria.getFrom() != null && logsSearchCriteria.getTo() != null) {
            if (logsSearchCriteria.getFrom().after(logsSearchCriteria.getTo())) {
                throw new IllegalArgumentException("Invalid time range, date 'from' greater then date 'to'");
            }
        }
        if (logsSearchCriteria.getPage() <= 0) {
            throw new IllegalArgumentException("Result start page must be greater then 0");
        }
        if (logsSearchCriteria.getPageSize() <= 0) {
            throw new IllegalArgumentException("Page size must be greater then or equal 1");
        }

        Optional<LogsSearchResult> logs = logDataRepository.searchLogsByCriteria(logsSearchCriteria);

        List<LogDataDto> logsDtos = new ArrayList<>();
        if (logs.isPresent()) {
            logs.ifPresent(logsSearchResult -> logsSearchResult.getResults().forEach(logData -> logsDtos.add(mapper.logDataToDto(logData))));
            return new LogDataSearchResultDto(logs.get().getTotalResults(), logsDtos);
        } else {
            throw new LogNotFoundException("No results for criteria");
        }
    }
}
