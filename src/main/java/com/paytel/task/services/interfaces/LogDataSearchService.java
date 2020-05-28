package com.paytel.task.services.interfaces;

import com.paytel.task.model.LogsSearchCriteria;
import com.paytel.task.model.dto.LogDataSearchResultDto;

public interface LogDataSearchService {

    LogDataSearchResultDto getLogsByCriteria(LogsSearchCriteria logsSearchCriteria);
}
